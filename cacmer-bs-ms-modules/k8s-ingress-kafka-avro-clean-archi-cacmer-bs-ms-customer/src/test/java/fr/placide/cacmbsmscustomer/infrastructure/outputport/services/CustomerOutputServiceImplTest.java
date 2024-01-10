package fr.placide.cacmbsmscustomer.infrastructure.outputport.services;

import fr.placide.cacmbsmscustomer.domain.avro.CustomerAvro;
import fr.placide.cacmbsmscustomer.domain.beans.Account;
import fr.placide.cacmbsmscustomer.domain.beans.Address;
import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.CustomerNotFoundException;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AccountModel;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AddressModel;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.proxies.AccountServiceProxy;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.proxies.AddressServiceProxy;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerDto;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerModel;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.repository.CustomerRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class CustomerOutputServiceImplTest {
    private static final String CUSTOMER_ID = "id";
    private static final String ADDRESS_ID = "id";
    private CustomerAvro avro;
    private Customer customer;
    private Address address;
    private static final String [] TOPICS ={"create","edit","delete"};
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private AddressServiceProxy addressServiceProxy;
    @Mock
    private AccountServiceProxy accountServiceProxy;
    @InjectMocks
    private CustomerOutputServiceImpl underTest;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        address = Address.builder()
                .addressId(ADDRESS_ID)
                .num(33)
                .street("allée Clémentine Deman")
                .pb(5900)
                .city("Lille")
                .country("France")
                .build();
        CustomerDto dto = CustomerDto.builder()
                .firstname("test")
                .lastname("test")
                .risk("low")
                .status("active")
                .addressId(ADDRESS_ID)
                .build();
        customer = Mapper.map(dto);
        customer.setCustomerId(CUSTOMER_ID);
        customer.setAddress(address);
        customer.setCreatedAt("now");

        avro = Mapper.toAvro(customer);
    }

    @Test
    void consumeCustomerEvents() {
        //EXECUTE
        Customer customer0 = underTest.consumeCustomerCreateEvent(avro,TOPICS[0]);
        Customer customer1 = underTest.consumeCustomerCreateEvent(avro,TOPICS[1]);
        Customer customer2 = underTest.consumeCustomerCreateEvent(avro,TOPICS[2]);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(customer0);
            Assertions.assertNotNull(customer1);
            Assertions.assertNotNull(customer2);
        });
    }

    @Test
    void createCustomer() {
        //PREPARE
        CustomerModel model = Mapper.map(customer);
        //EXECUTE
        Mockito.when(customerRepo.save(model)).thenReturn(model);
        underTest.createCustomer(customer);
        //VERIFY
        Assertions.assertAll("",()-> Mockito.verify(customerRepo, Mockito.times(1)).save(model));
    }

    @Test
    void getAllCustomers() {
        //PREPARE
        List<CustomerModel> customers = Stream.of(customer).map(Mapper::map).toList();
        //EXECUTE
        Mockito.when(customerRepo.findAll()).thenReturn(customers);
        List<Customer> customers1 = underTest.getAllCustomers();
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(customerRepo, Mockito.times(1)).findAll();
            Assertions.assertNotNull(customers1);
            Assertions.assertEquals(customers.size(), customers1.size());

        });
    }

    @Test
    void getCustomerByLastname() {
        //PREPARE
        String name ="lastname";
        List<CustomerModel> customers = Stream.of(customer).map(Mapper::map).toList();
        //EXECUTE
        Mockito.when(customerRepo.findByLastname(name)).thenReturn(customers);
        List<Customer> customers1 = underTest.getCustomerByLastname(name);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(customerRepo, Mockito.times(1)).findByLastname(name);
            Assertions.assertNotNull(customers1);
            Assertions.assertEquals(customers.size(), customers1.size());

        });
    }

    @Test
    void update() {
        //PREPARE
        Customer updated = customer;
        Address newAddress = address;
        newAddress.setNum(10);
        updated.setAddress(newAddress);
        //EXECUTE
        Mockito.when(customerRepo.save(Mapper.map(customer))).thenReturn(Mapper.map(updated));
        underTest.update(customer);
        //VERIFY
        Assertions.assertAll("",()-> Mockito.verify(customerRepo, Mockito.times(1)).save(Mapper.map(updated)));
    }
    @Test
    void delete() {
        //PREPARE
        //EXECUTE
        Mockito.doNothing().when(customerRepo).deleteById(customer.getCustomerId());
        underTest.delete(customer);
        //VERIFY
        Mockito.verify(customerRepo, Mockito.times(1)).deleteById(customer.getCustomerId());
    }

    @Test
    void getCustomersByAddress() {
        //PREPARE
        List<CustomerModel> models = Stream.of(customer).map(Mapper::map).toList();
        //EXECUTE
        Mockito.when(customerRepo.findByAddressId(ADDRESS_ID)).thenReturn(models);
        List<Customer> customers = underTest.getCustomersByAddress(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(customerRepo, Mockito.times(1)).findByAddressId(ADDRESS_ID);
            Assertions.assertNotNull(customers);
        });
    }

    @Test
    void getCustomerByFirstnameAndLastname() {
        //PREPARE
        String lastname = "lastname";
        String firstname = "firstname";
        List<CustomerModel> models = Stream.of(customer).map(Mapper::map).toList();
        //EXECUTE
        Mockito.when(customerRepo.findByFirstnameAndLastname(firstname, lastname)).thenReturn(models);
        List<Customer> customers = underTest.getCustomerByFirstnameAndLastname(firstname,lastname);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(customerRepo, Mockito.times(1))
                    .findByFirstnameAndLastname(firstname,lastname);
            Assertions.assertNotNull(customers);
        });
    }

    @Test
    void getCustomer() throws CustomerNotFoundException {
        Optional<CustomerModel> model = Optional.of(Mapper.map(customer));
        //EXECUTE
        Mockito.when(customerRepo.findById(CUSTOMER_ID)).thenReturn(model);
        Customer customer1 = underTest.getCustomer(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(customerRepo, Mockito.times(1)).findById(CUSTOMER_ID);
            Assertions.assertNotNull(customer1);
        });
    }

    @Test
    void getRemoteAddressById() {
        //PREPARE
        AddressModel model = AddressModel.builder()
                .addressId(address.getAddressId())
                .num(address.getNum())
                .build();
        //EXECUTE
        Mockito.when(addressServiceProxy.getRemoteAddressById(ADDRESS_ID)).thenReturn(model);
        Address address1 = underTest.getRemoteAddressById(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(addressServiceProxy, Mockito.times(1))
                    .getRemoteAddressById(ADDRESS_ID);
            Assertions.assertNotNull(address1);
        });
    }

    @Test
    void getRemoteAddressesByCity() {
        //PREPARE
        AddressModel model = AddressModel.builder()
                .addressId(address.getAddressId())
                .num(address.getNum())
                .build();
        String city ="Lille";
        //EXECUTE
        Mockito.when(addressServiceProxy.getRemoteAddressesByCity(city)).thenReturn(List.of(model));
        List<Address> addresses = underTest.getRemoteAddressesByCity(city);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(addressServiceProxy, Mockito.times(1))
                            .getRemoteAddressesByCity(city);
            Assertions.assertNotNull(addresses);
        });
    }

    @Test
    void getRemoteAccountsByCustomerId() {
        //PREPARE
        AccountModel model = AccountModel.builder()
                .accountId("account-id")
                .customerId(CUSTOMER_ID)
                .build();
        //EXECUTE
        Mockito.when(accountServiceProxy.getRemoteAccountsByCustomerId(CUSTOMER_ID)).thenReturn(List.of(model));
        List<Account> accounts = underTest.getRemoteAccountsByCustomerId(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(accountServiceProxy, Mockito.times(1))
                    .getRemoteAccountsByCustomerId(CUSTOMER_ID);
            Assertions.assertNotNull(accounts);
        });
    }
}