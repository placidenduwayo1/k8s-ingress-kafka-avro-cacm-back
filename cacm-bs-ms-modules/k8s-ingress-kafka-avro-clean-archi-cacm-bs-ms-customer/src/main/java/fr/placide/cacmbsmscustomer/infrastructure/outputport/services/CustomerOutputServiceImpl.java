package fr.placide.cacmbsmscustomer.infrastructure.outputport.services;

import fr.placide.cacmbsmscustomer.domain.avro.CustomerAvro;
import fr.placide.cacmbsmscustomer.domain.beans.Address;
import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.CustomerNotFoundException;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerConsumerService;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerOutputService;
import fr.placide.cacmbsmscustomer.domain.outputport.RemoteAddressOutputService;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AddressModel;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.proxies.AddressServiceProxy;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerModel;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerOutputServiceImpl implements CustomerOutputService, RemoteAddressOutputService, CustomerConsumerService {
    private final CustomerRepo customerRepo;
    private final AddressServiceProxy addressServiceProxy;

    public CustomerOutputServiceImpl(CustomerRepo customerRepo,@Qualifier(value ="addressserviceproxy")AddressServiceProxy addressServiceProxy) {
        this.customerRepo = customerRepo;
        this.addressServiceProxy = addressServiceProxy;
    }

    @Override
    @KafkaListener(topics = "customer-created",groupId = "customer")
    public Customer consumeCustomerCreateEvent(@Payload CustomerAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Customer customer = Mapper.map(avro);
        log.info("customer to create:<{}> consumed from topic:<{}>", customer, topic);
        return customer;
    }
    @Override
    public Customer createCustomer(Customer customer) {
        CustomerAvro avro = Mapper.toAvro(customer);
        Customer consumed = consumeCustomerCreateEvent(avro,"customer");
        return map(customerRepo.save(Mapper.map(consumed)));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return map(customerRepo.findAll());
    }
    @Override
    public List<Customer> getCustomerByLastname(String lastname) {
        return map(customerRepo.findByLastname(lastname));
    }

    @Override
    public Customer consumeCustomerEditEvent(CustomerAvro avro, String topic) {
        return null;
    }
    @Override
    public Customer updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer consumeCustomerDeleteEvent(CustomerAvro avro, String topic) {
        return null;
    }
    @Override
    public String deleteCustomer(String customerId) {
        customerRepo.deleteById(customerId);
        return "customer deleted";
    }

    @Override
    public List<Customer> getCustomerByFirstnameAndLastname(String firstname, String lastname) {
        return map(customerRepo.findByFirstnameAndLastname(firstname,lastname));
    }

    @Override
    public Customer getCustomer(String id) throws CustomerNotFoundException {
       return map(customerRepo.findById(id).orElseThrow(CustomerNotFoundException::new));
    }

    @Override
    public Address getRemoteAddressById(String addressId) {
        return map(addressServiceProxy.getRemoteAddressById(addressId));
    }

    @Override
    public List<Address> getRemoteAddressesByCity(String city) {
        return addressServiceProxy.getRemoteAddressesByCity(city).stream()
                .map(Mapper::map)
                .toList();
    }

    private Address map(AddressModel model){
        return Mapper.map(model);
    }
    private Customer map(CustomerModel model){
        return Mapper.map(model);
    }
    private List<Customer> map(List<CustomerModel> models){
        return models.stream()
                .map(Mapper::map)
                .toList();
    }
}
