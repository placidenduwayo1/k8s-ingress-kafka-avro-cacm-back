package fr.placide.cacmerbsmsaccount.infrastructure.outputport.services;

import fr.placide.cacmerbsmsaccount.domain.avro.AccountAvro;
import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.AccountNotFoundException;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.RemoteCustomerApiException;
import fr.placide.cacmerbsmsaccount.infrastructure.inputport.feignclients.models.CustomerModel;
import fr.placide.cacmerbsmsaccount.infrastructure.inputport.feignclients.proxies.CustomerServiceProxy;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountDto;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountModel;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.repository.AccountRepo;
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
import java.util.Optional;
import java.util.stream.Stream;

class AccountOutputServiceImplTest {
    @Mock
    private AccountRepo accountRepo;
    @Mock
    private CustomerServiceProxy customerServiceProxy;
    private static final String[] TOPICS = {"account-created", "edited", "deleted"};
    private Account account;
    private static final String ACCOUNT_ID = "account-id";
    private static final String CUSTOMER_ID = "customer-id";
    @InjectMocks
    private AccountOutputServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Customer customer = Customer.builder()
                .customerId(CUSTOMER_ID)
                .firstname("test")
                .lastname("test")
                .createdAt(Timestamp.from(Instant.from(Instant.now())).toString())
                .risk("low")
                .status("active")
                .build();
        AccountDto dto = AccountDto.builder()
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
    void consumeAccountEvent() {
        //PREPARE
        AccountAvro avro = Mapper.toAvro(account);
        //EXECUTE
        Account account0 = underTest.consumeAccountCreateEvent(avro, TOPICS[0]);
        Account account1 = underTest.consumeAccountEditEvent(avro, TOPICS[1]);
        Account account2 = underTest.consumeAccountDeleteEvent(avro, TOPICS[2]);
        //VERIFY
        Assertions.assertAll("", () -> {
            Assertions.assertNotNull(account0);
            Assertions.assertNotNull(account1);
            Assertions.assertNotNull(account2);
        });
    }

    @Test
    void create() {
        //PREPARE
        AccountModel model = Mapper.map(account);
        //EXECUTE
        Mockito.when(accountRepo.save(model)).thenReturn(Mockito.any(AccountModel.class));
        underTest.create(account);
        //VERIFY
        Mockito.verify(accountRepo, Mockito.atLeast(1)).save(Mockito.any());

    }

    @Test
    void getAll() {
        //PREPARE
        List<Account> accounts = List.of(account);
        //EXECUTE
        Mockito.when(accountRepo.findAll()).thenReturn(accounts.stream().map(Mapper::map).toList());
        List<Account> accounts1 = underTest.getAll();
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(accountRepo, Mockito.atLeast(1)).findAll();
            Assertions.assertNotNull(accounts1);
            Assertions.assertEquals(1, accounts1.size());
        });
    }

    @Test
    void get() throws AccountNotFoundException {
        //PREPARE
        AccountModel model = Mapper.map(account);
        //EXECUTE
        Mockito.when(accountRepo.findById(ACCOUNT_ID)).thenReturn(Optional.ofNullable(model));
        Account account1 = underTest.get(ACCOUNT_ID);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(accountRepo, Mockito.atLeast(1)).findById(ACCOUNT_ID);
            Assertions.assertNotNull(account1);
            Assertions.assertEquals(account.getAccountId(), account1.getAccountId());
        });
    }

    @Test
    void update() {
        //PREPARE
        AccountModel model = Mapper.map(account);
        //EXECUTE
        Mockito.when(accountRepo.save(model)).thenReturn(Mockito.any(AccountModel.class));
        underTest.update(account);
        //VERIFY
        Assertions.assertAll("", () -> Mockito.verify(accountRepo, Mockito.atLeast(1)).save(Mockito.any()));
    }

    @Test
    void delete() throws AccountNotFoundException {
        //PREPARE
        AccountModel model = Mapper.map(account);
        //EXECUTE
        Mockito.when(accountRepo.findById(ACCOUNT_ID)).thenReturn(Optional.ofNullable(model));
        Mockito.doNothing().when(accountRepo).deleteById(ACCOUNT_ID);
        underTest.delete(account.getAccountId());
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(accountRepo, Mockito.atLeast(1)).findById(ACCOUNT_ID);
            Mockito.verify(accountRepo, Mockito.atLeast(1)).deleteById(ACCOUNT_ID);
        });
    }

    @Test
    void getAccountsByCustomer() {
        //PREPARE
        List<AccountModel> models = List.of(Mapper.map(account));
        //EXECUTE
        Mockito.when(accountRepo.findByCustomerId(CUSTOMER_ID)).thenReturn(models);
        List<Account> accounts = underTest.getAccountsByCustomer(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertEquals(1,accounts.size());
            Mockito.verify(accountRepo, Mockito.atLeast(1)).findByCustomerId(CUSTOMER_ID);
        });
    }

    @Test
    void getRemoteCustomerById() throws RemoteCustomerApiException {
        //PREPARE
        CustomerModel model = Mapper.map(account.getCustomer());
        //EXECUTE
        Mockito.when(customerServiceProxy.getRemoteCustomer(account.getCustomerId())).thenReturn(model);
        Customer expected = underTest.getRemoteCustomerById(account.getCustomerId());
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(customerServiceProxy, Mockito.atLeast(1)).getRemoteCustomer(account.getCustomerId());
            Assertions.assertEquals(CUSTOMER_ID, expected.getCustomerId());
            Assertions.assertNotNull(expected);
        });
    }

    @Test
    void getCustomersByName() {
        //PREPARE
        String lastname="lastname";
        List<CustomerModel> models = Stream.of(account.getCustomer()).map(Mapper::map).toList();
        //EXECUTE
        Mockito.when(customerServiceProxy.getRemoteCustomersByName(lastname)).thenReturn(models);
        List<Customer> customers = underTest.getCustomersByName(lastname);

        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(customerServiceProxy, Mockito.atLeast(1)).getRemoteCustomersByName(lastname);
            Assertions.assertNotNull(customers);
            Assertions.assertEquals(models.size(), customers.size());
        });
    }
}