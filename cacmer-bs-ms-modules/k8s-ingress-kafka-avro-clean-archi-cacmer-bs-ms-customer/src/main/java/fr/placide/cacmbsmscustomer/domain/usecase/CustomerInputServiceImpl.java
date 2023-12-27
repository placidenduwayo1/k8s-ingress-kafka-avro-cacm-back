package fr.placide.cacmbsmscustomer.domain.usecase;

import fr.placide.cacmbsmscustomer.domain.avro.CustomerAvro;
import fr.placide.cacmbsmscustomer.domain.beans.Address;
import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.domain.exceptions.ExceptionMsg;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.*;
import fr.placide.cacmbsmscustomer.domain.inputport.CustomerInputService;
import fr.placide.cacmbsmscustomer.domain.inputport.RemoteAddressInputService;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerOutputService;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerProducerService;
import fr.placide.cacmbsmscustomer.domain.outputport.RemoteAddressOutputService;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerDto;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class CustomerInputServiceImpl implements CustomerInputService, RemoteAddressInputService {
    private final CustomerProducerService customerProducerService;
    private final CustomerOutputService customerOutputService;
    private final RemoteAddressOutputService remoteAddressOutputService;

    public CustomerInputServiceImpl(CustomerProducerService customerProducerService,
                                    CustomerOutputService customerOutputService, RemoteAddressOutputService remoteAddressOutputService) {
        this.customerProducerService = customerProducerService;
        this.customerOutputService = customerOutputService;
        this.remoteAddressOutputService = remoteAddressOutputService;
    }

    private void validateCustomerFields(CustomerDto dto) throws CustomerFieldsInvalidException, CustomerRiskInvalidException,
            CustomerStatusInvalidException, RemoteAddressApiException {
        if (!CustomerValidationTools.isValid(dto)) {
            throw new CustomerFieldsInvalidException();
        } else if (!CustomerValidationTools.isValidRisk(dto.getRisk())) {
            throw new CustomerRiskInvalidException();
        } else if (!CustomerValidationTools.isValidStatus(dto.getStatus())) {
            throw new CustomerStatusInvalidException();
        }
        Address address = getRemoteAddressById(dto.getAddressId());
        if (CustomerValidationTools.unreachableRemoteAddress(address.getAddressId())) {
            throw new RemoteAddressApiException(address.toString());
        }
    }

    private void validateCustomerExists(String firstname, String lastname) throws CustomerAlreadyExistsException {
        List<Customer> customers = customerOutputService.getCustomerByFirstnameAndLastname(firstname, lastname);
        if (!customers.isEmpty()) {
            throw new CustomerAlreadyExistsException();
        }
    }

    private void setDependency(Customer customer, String addressId) throws RemoteAddressApiException {
        Address address = getRemoteAddressById(addressId);
        customer.setAddressId(addressId);
        customer.setAddress(address);
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) throws CustomerFieldsInvalidException,
            CustomerRiskInvalidException, CustomerStatusInvalidException, CustomerAlreadyExistsException, RemoteAddressApiException {

        CustomerValidationTools.format(customerDto);
        validateCustomerFields(customerDto);
        validateCustomerExists(customerDto.getFirstname(), customerDto.getLastname());
        Customer bean = Mapper.map(customerDto);
        bean.setCustomerId(UUID.randomUUID().toString());
        bean.setCreatedAt(Timestamp.from(Instant.now()).toString());
        setDependency(bean, customerDto.getAddressId());
        CustomerAvro avro = Mapper.toAvro(bean);
        CustomerAvro produced = customerProducerService.produceKafkaEventCreateCustomer(avro);
        customerOutputService.createCustomer(Mapper.map(produced));
        return Mapper.map(produced);
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerOutputService.getAllCustomers();
        customers.forEach(customer -> {
            try {
                setDependency(customer, customer.getAddressId());
            } catch (RemoteAddressApiException e) {
                e.getMessage();
            }
        });
        return customers;
    }

    @Override
    public List<Customer> getCustomerByLastname(String lastname) throws CustomerNotFoundException {
        List<Customer> customers = customerOutputService.getCustomerByLastname(lastname);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        customers.forEach(customer -> {
            try {
                setDependency(customer, customer.getAddressId());
            } catch (RemoteAddressApiException e) {
                e.getMessage();
            }
        });
        return customers;
    }

    @Override
    public Customer updateCustomer(CustomerDto dto, String customerId) throws CustomerNotFoundException, RemoteAddressApiException,
            CustomerFieldsInvalidException, CustomerRiskInvalidException, CustomerStatusInvalidException {
        CustomerValidationTools.format(dto);
        validateCustomerFields(dto);
        Customer customer = getCustomer(customerId);
        customer.setFirstname(dto.getFirstname());
        customer.setLastname(dto.getLastname());
        customer.setRisk(dto.getRisk());
        customer.setStatus(dto.getStatus());
        customer.setAddressId(dto.getAddressId());
        customer.setAddress(getRemoteAddressById(dto.getAddressId()));
        CustomerAvro avro = customerProducerService.produceKafkaEventUpdateCustomer(Mapper.toAvro(customer));
        customerOutputService.update(Mapper.map(avro));
        return Mapper.map(avro);
    }

    @Override
    public String deleteCustomer(String customerId) throws CustomerNotFoundException, RemoteAddressApiException {
        CustomerAvro produced = customerProducerService.produceKafkaEventDeleteCustomer(Mapper.toAvro(getCustomer(customerId)));
        customerOutputService.delete(Mapper.map(produced));
        return "customer: " + produced + " deleted";
    }

    @Override
    public Customer getCustomer(String id) throws CustomerNotFoundException, RemoteAddressApiException {
        Customer customer = customerOutputService.getCustomer(id);
        setDependency(customer, customer.getAddressId());
        return customer;
    }

    @Override
    public List<Customer> getCustomersByAddress(String id) throws RemoteAddressApiException {
        List<Customer> customers = customerOutputService.getCustomersByAddress(id);
        if(customers.isEmpty()){
            throw new RemoteAddressApiException(ExceptionMsg.REMOTE_ADDRESS_API.getMsg());
        }
        for(Customer customer: customers){
            setDependency(customer, customer.getAddressId());
        }
        return customers;
    }

    @Override
    public List<Customer> getCustomersByCity(String city) throws RemoteAddressApiException {
        List<Address> addresses = getRemoteAddressesByCity(city);
        List<Customer> customers = new ArrayList<>();
        for(Address address:addresses){
           List<Customer> subList = getCustomersByAddress(address.getAddressId());
           for(Customer c: subList){
               if(c!=null){
                   setDependency(c, c.getAddressId());
               }
           }
           customers.addAll(subList);
        }
       return customers;
    }

    @Override
    public Address getRemoteAddressById(String addressId) throws RemoteAddressApiException {
        return remoteAddressOutputService.getRemoteAddressById(addressId);
    }

    @Override
    public List<Address> getRemoteAddressesByCity(String city) throws RemoteAddressApiException {

        List<Address> addresses = remoteAddressOutputService.getRemoteAddressesByCity(city);
        if (addresses.isEmpty()) {
            throw new RemoteAddressApiException(ExceptionMsg.REMOTE_ADDRESS_API.getMsg());
        }
        return addresses;
    }
}
