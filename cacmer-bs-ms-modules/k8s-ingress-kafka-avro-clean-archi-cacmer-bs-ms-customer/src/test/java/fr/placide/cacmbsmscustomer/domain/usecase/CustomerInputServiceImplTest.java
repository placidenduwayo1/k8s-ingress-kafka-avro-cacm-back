package fr.placide.cacmbsmscustomer.domain.usecase;

import fr.placide.cacmbsmscustomer.domain.avro.CustomerAvro;
import fr.placide.cacmbsmscustomer.domain.beans.Account;
import fr.placide.cacmbsmscustomer.domain.beans.Address;
import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.*;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerOutputService;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerProducerService;
import fr.placide.cacmbsmscustomer.domain.outputport.RemoteAccountOutputService;
import fr.placide.cacmbsmscustomer.domain.outputport.RemoteAddressOutputService;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

class CustomerInputServiceImplTest {
    @Mock
    private CustomerProducerService customerProducerService;
    @Mock
    private CustomerOutputService customerOutputService;
    @Mock
    private RemoteAddressOutputService remoteAddressOutputService;
    @Mock
    private RemoteAccountOutputService remoteAccountOutputService;
    @InjectMocks
    private CustomerInputServiceImpl underTest;
    private static final String CUSTOMER_ID = "id";
    private static final String ADDRESS_ID = "id";
    private Customer customer;
    private CustomerDto dto;
    private Address address;

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
        dto = CustomerDto.builder()
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
    }

    @Test
    void createCustomer() throws RemoteAddressApiException, CustomerFieldsInvalidException, CustomerRiskInvalidException,
            CustomerStatusInvalidException, CustomerAlreadyExistsException {
        //PREPARE
        CustomerAvro avro = Mapper.toAvro(customer);
        //EXECUTE
        Mockito.when(remoteAddressOutputService.getRemoteAddressById(customer.getAddressId())).thenReturn(address);
        Mockito.when(customerProducerService.produceKafkaEventCreateCustomer(Mockito.any(CustomerAvro.class))).thenReturn(avro);
        Mockito.doNothing().when(customerOutputService).createCustomer(customer);
        Customer customer1 = underTest.createCustomer(dto);
        Assertions.assertAll("", () -> {
            Mockito.verify(remoteAddressOutputService, Mockito.atLeast(1)).getRemoteAddressById(customer.getAddressId());
            Mockito.verify(customerProducerService, Mockito.atLeast(1)).produceKafkaEventCreateCustomer(Mockito.any());
            Assertions.assertEquals(customer.getCustomerId(), customer1.getCustomerId());
        });
    }

    @Test
    void getAllCustomers() {
        //PREPARE
        List<Customer> customers = List.of(customer);
        //EXECUTE
        Mockito.when(customerOutputService.getAllCustomers()).thenReturn(customers);
        List<Customer> customers1 = underTest.getAllCustomers();
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(customerOutputService, Mockito.atLeast(1)).getAllCustomers();
            Assertions.assertNotNull(customers1);
        });
    }

    @Test
    void getCustomerByLastname() throws CustomerNotFoundException {
        //PREPARE
        String lastname = "lastname";
        //EXECUTE
        Mockito.when(customerOutputService.getCustomerByLastname(lastname)).thenReturn(List.of(customer));
        List<Customer> customers = underTest.getCustomerByLastname(lastname);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(customerOutputService, Mockito.atLeast(1)).getCustomerByLastname(lastname);
            Assertions.assertNotNull(customers);
        });
    }
    @Test
    void getCustomerByLastnameException(){
        //PREPARE
        String lastname = "lastname";
        //EXECUTE
        Mockito.when(customerOutputService.getCustomerByLastname(lastname)).thenReturn(Collections.emptyList());
        Exception exception = Assertions.assertThrows(CustomerNotFoundException.class,()-> underTest.getCustomerByLastname(lastname));
        Assertions.assertAll("",()->{
            Mockito.verify(customerOutputService, Mockito.atLeast(1)).getCustomerByLastname(lastname);
            Assertions.assertNotNull(exception);
        });
    }

    @Test
    void updateCustomer() throws CustomerNotFoundException, RemoteAddressApiException, CustomerFieldsInvalidException, CustomerRiskInvalidException,
            CustomerStatusInvalidException {
        //PREPARE
        CustomerAvro avro = Mapper.toAvro(customer);
        //EXECUTE
        Mockito.when(remoteAddressOutputService.getRemoteAddressById(customer.getAddressId())).thenReturn(address);
        Mockito.when(customerOutputService.getCustomer(CUSTOMER_ID)).thenReturn(customer);
        Mockito.when(customerProducerService.produceKafkaEventUpdateCustomer(Mockito.any())).thenReturn(avro);
        Mockito.doNothing().when(customerOutputService).update(customer);
        underTest.updateCustomer(dto, CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(customerOutputService, Mockito.atLeast(1)).getCustomer(CUSTOMER_ID);
            Mockito.verify(customerProducerService, Mockito.atLeast(1)).produceKafkaEventUpdateCustomer(Mockito.any());
        });
    }

    @Test
    void deleteCustomer() throws CustomerNotFoundException, CustomerAssignedAccountException, RemoteAddressApiException, RemoteAccountApiException {
        //PREPARE
        CustomerAvro avro = Mapper.toAvro(customer);
        List<Account> emptyList = Collections.emptyList();
        //EXECUTE
        Mockito.when(customerOutputService.getCustomer(CUSTOMER_ID)).thenReturn(customer);
        Mockito.when(remoteAccountOutputService.getRemoteAccountsByCustomerId(CUSTOMER_ID)).thenReturn(emptyList);
        Mockito.when(customerProducerService.produceKafkaEventDeleteCustomer(Mockito.any())).thenReturn(avro);
        Mockito.when(remoteAddressOutputService.getRemoteAddressById(customer.getAddressId())).thenReturn(address);
        Mockito.doNothing().when(customerOutputService).delete(customer);
        underTest.deleteCustomer(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(customerOutputService, Mockito.atLeast(1)).getCustomer(CUSTOMER_ID);
            Mockito.verify(customerProducerService, Mockito.times(1)).produceKafkaEventDeleteCustomer(Mockito.any());
            Mockito.verify(remoteAddressOutputService, Mockito.times(1)).getRemoteAddressById(customer.getAddressId());
        });
    }

    @Test
    void testCustomerAssignedAccountsException() throws RemoteAccountApiException, CustomerNotFoundException {
        //PREPARE
        Account account = Account.builder()
                .accountId("account-id")
                .customerId(CUSTOMER_ID)
                .build();
        //EXECUTE
        Mockito.when(customerOutputService.getCustomer(CUSTOMER_ID)).thenReturn(customer);
        Mockito.when(remoteAddressOutputService.getRemoteAddressById(ADDRESS_ID)).thenReturn(address);
        Mockito.when(remoteAccountOutputService.getRemoteAccountsByCustomerId(CUSTOMER_ID)).thenReturn(List.of(account));
        Exception exception = Assertions.assertThrows(CustomerAssignedAccountException.class,()-> underTest.deleteCustomer(CUSTOMER_ID));
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(customerOutputService, Mockito.times(1)).getCustomer(CUSTOMER_ID);
            Mockito.verify(remoteAddressOutputService, Mockito.times(1)).getRemoteAddressById(ADDRESS_ID);
            Assertions.assertNotNull(exception);
        });
    }

    @Test
    void getCustomer() throws CustomerNotFoundException, RemoteAddressApiException {
        //PREPARE
        //EXECUTE
        Mockito.when(remoteAddressOutputService.getRemoteAddressById(ADDRESS_ID)).thenReturn(address);
        Mockito.when(customerOutputService.getCustomer(CUSTOMER_ID)).thenReturn(customer);
        Customer customer1 = underTest.getCustomer(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("", () -> {
            Assertions.assertNotNull(customer1);
            Mockito.verify(remoteAddressOutputService, Mockito.times(1)).getRemoteAddressById(ADDRESS_ID);
            Mockito.verify(customerOutputService, Mockito.times(1)).getCustomer(CUSTOMER_ID);
        });
    }

    @Test
    void getCustomersByAddress() {
        //PREPARE
        //EXECUTE
        Mockito.when(remoteAddressOutputService.getRemoteAddressById(ADDRESS_ID)).thenReturn(address);
        Mockito.when(customerOutputService.getCustomersByAddress(ADDRESS_ID)).thenReturn(List.of(customer));
        List<Customer> customers = underTest.getCustomersByAddress(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("", () -> {
            Assertions.assertNotNull(customers);
            Mockito.verify(customerOutputService, Mockito.times(1)).getCustomersByAddress(ADDRESS_ID);
        });
    }

    @Test
    void getCustomersByCity() throws RemoteAddressApiException {
        //PREPARE
        String city = "city";

        //EXECUTE
        Mockito.when(remoteAddressOutputService.getRemoteAddressesByCity(city)).thenReturn(List.of(address));
        List.of(address).forEach(address1 -> {
            Mockito.when(remoteAddressOutputService.getRemoteAddressById(address1.getAddressId())).thenReturn(address1);
            Mockito.when(customerOutputService.getCustomersByAddress(address1.getAddressId())).thenReturn(List.of(customer));
        });
        List<Customer> customers = underTest.getCustomersByCity(city);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(remoteAddressOutputService, Mockito.atLeast(1)).getRemoteAddressesByCity(city);
            List.of(address).forEach(address1 -> Mockito.verify(customerOutputService, Mockito.times(1))
                    .getCustomersByAddress(address1.getAddressId()));
        });
        Assertions.assertNotNull(customers);
    }
    @Test
    void getRemoteAddressById() throws RemoteAddressApiException {
        //EXECUTE
        Mockito.when(remoteAddressOutputService.getRemoteAddressById(ADDRESS_ID)).thenReturn(address);
        Address address1 = underTest.getRemoteAddressById(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(remoteAddressOutputService, Mockito.times(1)).getRemoteAddressById(ADDRESS_ID);
            Assertions.assertNotNull(address1);
        });
    }
    @Test
    void getRemoteAddressesByCity() throws RemoteAddressApiException {
        //PREPARE
        String city = "city";
        //EXECUTE
        Mockito.when(remoteAddressOutputService.getRemoteAddressesByCity(city)).thenReturn(List.of(address));
        List<Address> addresses = underTest.getRemoteAddressesByCity(city);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(remoteAddressOutputService, Mockito.atLeast(1)).getRemoteAddressesByCity(city);
            Assertions.assertNotNull(addresses);
        });
    }
    @Test
    void testRemoteAddressByCityException() throws RemoteAddressApiException {
        //PREPARE
        String city ="city";
        //EXECUTE
        Mockito.when(remoteAddressOutputService.getRemoteAddressesByCity(city)).thenReturn(Collections.emptyList());
        RemoteAddressApiException exception = Assertions.assertThrows(RemoteAddressApiException.class, ()-> underTest.getRemoteAddressesByCity(city));
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(remoteAddressOutputService, Mockito.times(1)).getRemoteAddressesByCity(city);
            Assertions.assertNotNull(exception);
        });
    }
    @Test
    void testRemoteAddressApiException(){
        //EXECUTE
        Mockito.when(remoteAddressOutputService.getRemoteAddressById(ADDRESS_ID)).thenReturn(null);
        RemoteAddressApiException exception = Assertions.assertThrows(
                RemoteAddressApiException.class,()->underTest.getRemoteAddressById(ADDRESS_ID));
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(remoteAddressOutputService, Mockito.atLeast(1)).getRemoteAddressById(ADDRESS_ID);
            Assertions.assertNotNull(exception);
        });
    }
}