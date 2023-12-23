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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CustomerInputServiceImpl implements CustomerInputService, RemoteAddressInputService {
    private final CustomerProducerService customerProducerService;
    private final CustomerOutputService customerOutputService;
    private final RemoteAddressOutputService remoteAddressOutputService;

    public CustomerInputServiceImpl(CustomerProducerService customerProducerService, CustomerOutputService customerOutputService,
                                    RemoteAddressOutputService remoteAddressOutputService) {
        this.customerProducerService = customerProducerService;
        this.customerOutputService = customerOutputService;
        this.remoteAddressOutputService = remoteAddressOutputService;
    }

    private void validateCustomerFields(CustomerDto dto) throws CustomerFieldsInvalidException, CustomerRiskInvalidException,
            CustomerStatusInvalidException,
            RemoteAddressApiException {
        if(!CustomerValidationTools.isValid(dto)){
            throw new CustomerFieldsInvalidException();
        } else if (!CustomerValidationTools.isValidRisk(dto.getRisk())) {
            throw new CustomerRiskInvalidException();
        } else if (!CustomerValidationTools.isValidStatus(dto.getStatus())) {
            throw new CustomerStatusInvalidException();
        }
        Address address = getRemoteAddressById(dto.getAddressId());
        if(CustomerValidationTools.unreachableRemoteAddress(address.getAddressId())){
            throw new RemoteAddressApiException(address.toString());
        }
    }

    private void validateCustomerExists(String firstname, String lastname) throws CustomerAlreadyExistsException {
        List<Customer> customers = customerOutputService.getCustomerByFirstnameAndLastname(firstname,lastname);
        if(!customers.isEmpty()){
            throw new CustomerAlreadyExistsException();
        }
    }
    @Override
    public Customer createCustomer(CustomerDto customerDto) throws RemoteAddressApiException, CustomerFieldsInvalidException,
            CustomerRiskInvalidException, CustomerStatusInvalidException, CustomerAlreadyExistsException {

        CustomerValidationTools.format(customerDto);
        validateCustomerFields(customerDto);
        validateCustomerExists(customerDto.getFirstname(), customerDto.getLastname());
        Customer bean = Mapper.map(customerDto);
        bean.setCustomerId(UUID.randomUUID().toString());
        bean.setCreatedAt(Timestamp.from(Instant.now()).toString());
        CustomerAvro avro = Mapper.toAvro(bean);
        Customer customer = Mapper.map(customerProducerService.produceKafkaEventCreateCustomer(avro));
        return customerOutputService.createCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerOutputService.getAllCustomers();
    }

    @Override
    public List<Customer> getCustomerByLastname(String lastname) throws CustomerNotFoundException {
        List<Customer> customers = customerOutputService.getCustomerByLastname(lastname);
        if(customers.isEmpty()){
            throw new CustomerNotFoundException();
        }
        return customers;
    }

    @Override
    public Customer updateCustomer(CustomerDto dto, String customerId) throws CustomerNotFoundException, RemoteAddressApiException {
        return null;
    }

    @Override
    public String deleteCustomer(String customerId) throws CustomerNotFoundException {
        return null;
    }

    @Override
    public Customer getCustomer(String id) throws CustomerNotFoundException {
        return customerOutputService.getCustomer(id);
    }

    @Override
    public Address getRemoteAddressById(String addressId) throws RemoteAddressApiException {
        Address address= remoteAddressOutputService.getRemoteAddressById(addressId);
        if(address.getAddressId().equals(ExceptionMsg.REMOTE_ADDRESS_API.getMsg())){
            throw new RemoteAddressApiException(address.toString());
        }
        return address;
    }

    @Override
    public List<Address> getRemoteAddressesByCity(String city) throws RemoteAddressApiException {

        List<Address> addresses= remoteAddressOutputService.getRemoteAddressesByCity(city);
        if(addresses.isEmpty()){
            throw new RemoteAddressApiException(ExceptionMsg.REMOTE_ADDRESS_API.getMsg());
        }
        return addresses;
    }
}
