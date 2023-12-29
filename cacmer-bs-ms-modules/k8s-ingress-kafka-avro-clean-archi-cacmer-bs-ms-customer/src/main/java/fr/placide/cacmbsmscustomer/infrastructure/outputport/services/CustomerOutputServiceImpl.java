package fr.placide.cacmbsmscustomer.infrastructure.outputport.services;

import fr.placide.cacmbsmscustomer.domain.avro.CustomerAvro;
import fr.placide.cacmbsmscustomer.domain.beans.Account;
import fr.placide.cacmbsmscustomer.domain.beans.Address;
import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.CustomerNotFoundException;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerConsumerService;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerOutputService;
import fr.placide.cacmbsmscustomer.domain.outputport.RemoteAccountOutputService;
import fr.placide.cacmbsmscustomer.domain.outputport.RemoteAddressOutputService;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AddressModel;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.proxies.AccountServiceProxy;
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
public class CustomerOutputServiceImpl implements CustomerOutputService, RemoteAddressOutputService, CustomerConsumerService,
        RemoteAccountOutputService {
    private final CustomerRepo customerRepo;
    private final AddressServiceProxy addressServiceProxy;
    private final AccountServiceProxy accountServiceProxy;

    public CustomerOutputServiceImpl(CustomerRepo customerRepo,
                                     @Qualifier(value = "addressserviceproxy") AddressServiceProxy addressServiceProxy,
                                     @Qualifier(value = "accountserviceproxy") AccountServiceProxy accountServiceProxy) {
        this.customerRepo = customerRepo;
        this.addressServiceProxy = addressServiceProxy;
        this.accountServiceProxy = accountServiceProxy;
    }

    @Override
    @KafkaListener(topics = "customer-created", groupId = "customer")
    public Customer consumeCustomerCreateEvent(@Payload CustomerAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
      return map(avro);
    }

    @Override
    public void createCustomer(Customer customer) {
        CustomerAvro avro = Mapper.toAvro(customer);
        Customer consumed = consumeCustomerCreateEvent(avro, "customer");
        log.info("customer to create:<{}> consumed from topic:<{}>", consumed, "customer-created");
        customerRepo.save(Mapper.map(consumed));
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
    @KafkaListener(topics = "customer-edited", groupId = "customer")
    public Customer consumeCustomerEditEvent(@Payload CustomerAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
       return map(avro);
    }

    @Override
    public void update(Customer customer) {
        CustomerAvro avro = Mapper.toAvro(customer);
        Customer consumed = consumeCustomerEditEvent(avro, "customer-edited");
        log.info("customer to update:<{}> consumed from topic:<{}>", consumed, "customer-edited");
        customerRepo.save(Mapper.map(consumed));
    }


    @Override
    @KafkaListener(topics = "customer-deleted", groupId = "customer")
    public Customer consumeCustomerDeleteEvent(@Payload CustomerAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
       return  map(avro);
    }

    @Override
    public void delete(Customer customer) {
        CustomerAvro avro = Mapper.toAvro(customer);
        Customer consumed = consumeCustomerDeleteEvent(avro,"customer-deleted");
        log.info("customer to delete:<{}> consumed from topic:<{}>", consumed, "customer-deleted");
        customerRepo.deleteById(consumed.getCustomerId());
    }

    @Override
    public List<Customer> getCustomersByAddress(String id) {
        return map(customerRepo.findByAddressId(id));
    }

    @Override
    public List<Customer> getCustomerByFirstnameAndLastname(String firstname, String lastname) {
        return map(customerRepo.findByFirstnameAndLastname(firstname, lastname));
    }

    @Override
    public Customer getCustomer(String id) throws CustomerNotFoundException {
        return map(customerRepo.findById(id).orElseThrow(CustomerNotFoundException::new));
    }
    @Override
    public Address getRemoteAddressById(String addressId) {
        AddressModel model = addressServiceProxy.getRemoteAddressById(addressId);
        Address bean = null;
        if(model!=null){
            bean = map(model);
        }
        return bean;
    }
    @Override
    public List<Address> getRemoteAddressesByCity(String city) {
        return addressServiceProxy.getRemoteAddressesByCity(city).stream()
                .map(Mapper::map)
                .toList();
    }

    private Address map(AddressModel model) {
        return Mapper.map(model);
    }

    private Customer map(CustomerModel model) {
        return Mapper.map(model);
    }

    private List<Customer> map(List<CustomerModel> models) {
        return models.stream()
                .map(Mapper::map)
                .toList();
    }

    private Customer map(CustomerAvro avro) {
        return Mapper.map(avro);
    }

    @Override
    public List<Account> getRemoteAccountsByCustomerId(String customerId) {
        return accountServiceProxy.getRemoteAccountsByCustomerId(customerId).stream()
                .map(Mapper::map)
                .toList();
    }
}
