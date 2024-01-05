package fr.placide.cacmerbsmsaccount.domain.usecase;

import fr.placide.cacmerbsmsaccount.domain.avro.AccountAvro;
import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.*;
import fr.placide.cacmerbsmsaccount.domain.outputport.AccountOutputService;
import fr.placide.cacmerbsmsaccount.domain.outputport.AccountProducerService;
import fr.placide.cacmerbsmsaccount.domain.outputport.RemoteCustomerOutputService;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

class AccountInputServiceImplTest {
    @Mock
    private RemoteCustomerOutputService remoteCustomerOutputService;
    @Mock
    private AccountOutputService accountOutputService;
    @Mock
    private AccountProducerService accountProducerService;
    @InjectMocks
    private AccountInputServiceImpl underTest;
    private Account account;
    private static final String ACCOUNT_ID = "account-id";
    private Customer customer;
    private static final String CUSTOMER_ID = "customer-id";
    private AccountDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = Customer.builder()
                .customerId(CUSTOMER_ID)
                .firstname("test")
                .lastname("test")
                .createdAt(Timestamp.from(Instant.from(Instant.now())).toString())
                .risk("low")
                .status("active")
                .build();
        dto = AccountDto.builder()
                .type("current")
                .balance(50)
                .overdraft(0)
                .customerId(CUSTOMER_ID)
                .build();
        account = Mapper.map(dto);
        account.setCustomer(customer);
        account.setAccountId(ACCOUNT_ID);
    }

    @Test
    void createAccount() throws RemoteCustomerApiException, RemoteCustomerApiStatusInvalidException,
            AccountTypeInvalidException, AccountFieldsInvalidException {
        //PREPARE
        AccountAvro avro = Mapper.toAvro(account);
        //EXECUTE
        Mockito.when(remoteCustomerOutputService.getRemoteCustomerById(CUSTOMER_ID)).thenReturn(customer);
        Mockito.when(accountProducerService.produceKafkaEventCreateAccount(Mockito.any(AccountAvro.class))).thenReturn(avro);
        Mockito.doNothing().when(accountOutputService).create(account);
        Account created = underTest.createAccount(dto);
        //VERIFY
        Assertions.assertAll("gpe of assertions", () -> {
            Mockito.verify(remoteCustomerOutputService, Mockito.atLeast(1)).getRemoteCustomerById(CUSTOMER_ID);
            Mockito.verify(accountProducerService, Mockito.atLeast(1)).produceKafkaEventCreateAccount(Mockito.any());
            Assertions.assertNotNull(created);
        });
    }

    @Test
    void getAllAccounts() {
        //PREPARE
        List<Account> accounts = List.of(account);
        //EXECUTE
        Mockito.when(accountOutputService.getAll()).thenReturn(accounts);
        List<Account> expected = underTest.getAllAccounts();
        //VERIFY
        Assertions.assertAll("", () -> {
            Assertions.assertEquals(expected, accounts);
            Mockito.verify(accountOutputService, Mockito.atLeast(1)).getAll();
        });
    }

    @Test
    void update() throws AccountNotFoundException, RemoteCustomerApiException, RemoteCustomerApiStatusInvalidException,
            AccountTypeInvalidException, AccountFieldsInvalidException {
        //PREPARE
        AccountAvro avro = Mapper.toAvro(account);
        //EXECUTE
        Mockito.when(accountOutputService.get(ACCOUNT_ID)).thenReturn(account);
        Mockito.when(remoteCustomerOutputService.getRemoteCustomerById(account.getCustomerId())).thenReturn(customer);
        Mockito.when(accountProducerService.produceKafkaEventUpdateAccount(Mockito.any(AccountAvro.class))).thenReturn(avro);
        Mockito.doNothing().when(accountOutputService).update(account);
        Account expected = underTest.update(dto, ACCOUNT_ID);
        //VERIFY
        Assertions.assertAll("", () -> {
            Assertions.assertNotNull(expected);
            Mockito.verify(accountOutputService, Mockito.atLeast(1)).get(ACCOUNT_ID);
            Mockito.verify(accountProducerService, Mockito.atLeast(1)).produceKafkaEventUpdateAccount(Mockito.any());
            Mockito.verify(remoteCustomerOutputService, Mockito.atLeast(1)).getRemoteCustomerById(account.getCustomerId());
        });
    }

    @Test
    void deleteAccount() throws AccountNotFoundException, RemoteCustomerApiException {
        //PREPARE
        AccountAvro avro = Mapper.toAvro(account);
        //EXECUTE
        Mockito.when(accountOutputService.get(ACCOUNT_ID)).thenReturn(account);
        Mockito.when(remoteCustomerOutputService.getRemoteCustomerById(CUSTOMER_ID)).thenReturn(customer);
        Mockito.when(accountProducerService.produceKafkaEventDeleteAccount(avro)).thenReturn(avro);
        accountProducerService.produceKafkaEventCreateAccount(avro);
        Mockito.doNothing().when(accountOutputService).delete(ACCOUNT_ID);
        String expected = underTest.deleteAccount(ACCOUNT_ID);
        //VERIFY
        Assertions.assertAll("", () -> {
            Assertions.assertNotNull(expected);
            Mockito.verify(accountOutputService, Mockito.atLeast(1)).get(ACCOUNT_ID);
            Mockito.verify(remoteCustomerOutputService, Mockito.atLeast(1)).getRemoteCustomerById(CUSTOMER_ID);
            Mockito.verify(accountProducerService, Mockito.atLeast(1)).produceKafkaEventDeleteAccount(avro);
            Mockito.verify(accountOutputService, Mockito.atLeast(1)).delete(ACCOUNT_ID);
        });
    }

    @Test
    void getAccountById() throws AccountNotFoundException {
        //PREPARE
        //EXECUTE
        Mockito.when(accountOutputService.get(ACCOUNT_ID)).thenReturn(account);
        Account expected = underTest.getAccountById(ACCOUNT_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertEquals(expected,account);
            Mockito.verify(accountOutputService, Mockito.atLeast(1)).get(ACCOUNT_ID);
        });
    }

    @Test
    void getAccountsByCustomer() {
        //PREPARE
        List<Account> accounts = List.of(account);
        //EXECUTE
        Mockito.when(accountOutputService.getAccountsByCustomer(CUSTOMER_ID)).thenReturn(accounts);
        List<Account> expected = underTest.getAccountsByCustomer(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertEquals(expected,accounts);
            Mockito.verify(accountOutputService, Mockito.atLeast(1)).getAccountsByCustomer(CUSTOMER_ID);
        });
    }

    @Test
    void getAccountsByCustomersName() throws RemoteCustomerApiException {
        //PREPARE
        List<Account> accounts = List.of(account);
        String customerLastname="lastname";
        List<Customer> customers = List.of(customer);
        //EXECUTE
        Mockito.when(remoteCustomerOutputService.getCustomersByName(customerLastname)).thenReturn(customers);
        for(Customer c: customers){
            Mockito.when(remoteCustomerOutputService.getRemoteCustomerById(c.getCustomerId())).thenReturn(customer);
            Mockito.when(accountOutputService.getAccountsByCustomer(c.getCustomerId())).thenReturn(accounts);
        }
        List<Account> exp = underTest.getAccountsByCustomersName(customerLastname);
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(exp);
            Mockito.verify(remoteCustomerOutputService, Mockito.atLeast(1)).getCustomersByName(customerLastname);
            for (Customer c: customers){
                Mockito.verify(remoteCustomerOutputService, Mockito.atLeast(1)).getRemoteCustomerById(c.getCustomerId());
            }
        });


    }

    @Test
    void getRemoteCustomerById() throws RemoteCustomerApiException {
        //PREPARE
        //EXECUTE
        Mockito.when(remoteCustomerOutputService.getRemoteCustomerById(CUSTOMER_ID)).thenReturn(customer);
        Customer expected = underTest.getRemoteCustomerById(CUSTOMER_ID);
        //EXECUTE
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(expected);
            Mockito.verify(remoteCustomerOutputService, Mockito.atLeast(1)).getRemoteCustomerById(CUSTOMER_ID);
        });
    }

    @Test
    void getRemoteCustomersByName() throws RemoteCustomerApiException {
        //PREPARE
        String customerLastname="lastname";
        List<Customer> customers = List.of(customer);
        //EXECUTE
        Mockito.when(remoteCustomerOutputService.getCustomersByName(customerLastname)).thenReturn(customers);
        List<Customer> expected = underTest.getRemoteCustomersByName(customerLastname);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(expected);
            Mockito.verify(remoteCustomerOutputService, Mockito.atLeast(1)).getCustomersByName(customerLastname);
        });

    }
}