package fr.placide.cacmbsmscustomer.infrastructure.inputport.web;

import fr.placide.cacmbsmscustomer.domain.beans.Address;
import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.*;
import fr.placide.cacmbsmscustomer.domain.inputport.CustomerInputService;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

class ControllerTest {
    @Mock
    private CustomerInputService inputService;
    @InjectMocks
    private Controller underTest;
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
    void gets() {
        //PREPARE
        List<Customer> customers = List.of(customer);
        //EXECUTE
        Mockito.when(inputService.getAllCustomers()).thenReturn(customers);
        List<Customer> customers1 = underTest.gets();
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(inputService, Mockito.atLeast(1)).getAllCustomers();
            Assertions.assertNotNull(customers1);
            Assertions.assertEquals(1,customers1.size());
        });
    }

    @Test
    void create() throws RemoteAddressApiException, CustomerFieldsInvalidException, CustomerRiskInvalidException,
            CustomerStatusInvalidException, CustomerAlreadyExistsException {
        //EXECUTE
        Mockito.when(inputService.createCustomer(dto)).thenReturn(customer);
        Customer customer1 = underTest.create(dto);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(inputService, Mockito.atLeast(1)).createCustomer(dto);
            Assertions.assertNotNull(customer1);
            Assertions.assertEquals(customer,customer1);
        });
    }

    @Test
    void testGets() throws CustomerNotFoundException {
        //PREPARE
        String lastname = "test";
        //EXECUTE
        Mockito.when(inputService.getCustomerByLastname(lastname)).thenReturn(List.of(customer));
        List<Customer> customers = underTest.gets(lastname);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(inputService, Mockito.atLeast(1)).getCustomerByLastname(lastname);
            Assertions.assertNotNull(customers);
            Assertions.assertEquals(1, customers.size());
        });
    }

    @Test
    void get() throws RemoteAddressApiException, CustomerNotFoundException {
        //EXECUTE
        Mockito.when(inputService.getCustomer(CUSTOMER_ID)).thenReturn(customer);
        Customer customer1 = underTest.get(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(inputService, Mockito.atLeast(1)).getCustomer(CUSTOMER_ID);
            Assertions.assertNotNull(customer1);
            Assertions.assertEquals(customer,customer1);
        });
    }

    @Test
    void update() throws RemoteAddressApiException, CustomerFieldsInvalidException, CustomerRiskInvalidException,
            CustomerStatusInvalidException, CustomerNotFoundException {
        //PREPARE
        Customer customer1 = customer;
        Address address1 = address;
        address1.setNum(50);
        customer1.setAddress(address1);
        //EXECUTE
        Mockito.when(inputService.updateCustomer(dto, CUSTOMER_ID)).thenReturn(customer1);
        Customer customer11 = underTest.update(dto, CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(inputService, Mockito.atLeast(1)).updateCustomer(dto, CUSTOMER_ID);
            Assertions.assertNotNull(customer11);
            Assertions.assertEquals(50,customer11.getAddress().getNum());
        });
    }

    @Test
    void delete() throws CustomerAssignedAccountException, RemoteAddressApiException, RemoteAccountApiException, CustomerNotFoundException {
        //EXECUTE
        Mockito.when(inputService.deleteCustomer(CUSTOMER_ID)).thenReturn("deleted");
        String s = underTest.delete(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(s);
            Assertions.assertEquals("deleted",s);
            Mockito.verify(inputService, Mockito.atLeast(1)).deleteCustomer(CUSTOMER_ID);
        });

    }

    @Test
    void getCustomersByAddress() {
        //EXECUTE
        Mockito.when(inputService.getCustomersByAddress(ADDRESS_ID)).thenReturn((List.of(customer)));
        List<Customer> customers = underTest.getCustomersByAddress(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertEquals(List.of(customer),customers);
            Assertions.assertNotNull(customers);
            Assertions.assertEquals(1, customers.size());
            Mockito.verify(inputService, Mockito.atLeast(1)).getCustomersByAddress(ADDRESS_ID);
        });
    }

    @Test
    void getCustomersByCity() throws RemoteAddressApiException {
        //PREPARE
        String city ="city";
        //EXECUTE
        Mockito.when(inputService.getCustomersByCity(city)).thenReturn(List.of(customer));
        List<Customer> customers = underTest.getCustomersByCity(city);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertEquals(List.of(customer),customers);
            Assertions.assertEquals(List.of(customer).size(), customers.size());
            Mockito.verify(inputService, Mockito.atLeast(1)).getCustomersByCity(city);
        });
    }
}