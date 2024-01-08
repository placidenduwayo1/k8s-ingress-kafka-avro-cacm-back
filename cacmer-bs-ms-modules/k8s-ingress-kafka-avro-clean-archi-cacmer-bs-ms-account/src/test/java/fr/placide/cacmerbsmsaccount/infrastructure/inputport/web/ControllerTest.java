package fr.placide.cacmerbsmsaccount.infrastructure.inputport.web;

import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.*;
import fr.placide.cacmerbsmsaccount.domain.inputport.AccountInputService;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

class ControllerTest {
    @Mock
    private AccountInputService accountInputService;
    @InjectMocks
    private Controller underTest;
    private static final String CUSTOMER_ID = "id";
    private static final String ACCOUNT_ID = "id";
    private Account account;
    private AccountDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dto = AccountDto.builder()
                .type("current")
                .balance(100)
                .overdraft(100)
                .customerId(CUSTOMER_ID)
                .build();
        account = Mapper.map(dto);
    }

    @Test
    void getWelcome() {
        //PREPARE
        Map<String, String> welcome = Map.of("test-key","test-value");
        String value ="We wish you a warm welcome to account api service";
        String key="bs-ms-account-api";
        // EXECUTE
        Map<String, String> expected = underTest.getWelcome();
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(expected);
            Assertions.assertNotEquals(welcome, expected);
            Assertions.assertEquals(value,expected.get(key));
        });
    }

    @Test
    void getAccountsByCustomersName() throws RemoteCustomerApiException {
        //PREPARE
        account.setAccountId(ACCOUNT_ID);
        List<Account> accounts = List.of(account);
        String lastname="lastname";
        //EXECUTE
        Mockito.when(accountInputService.getAccountsByCustomersName(lastname)).thenReturn(accounts);
        List<Account> accounts1 = underTest.getAccountsByCustomersName(lastname);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(accountInputService, Mockito.atLeast(1)).getAccountsByCustomersName(lastname);
            Assertions.assertEquals(accounts.size(), accounts1.size());
        });
    }

    @Test
    void getAccountByCustomerId() {
        //PREPARE
        List<Account> accounts = List.of(account);
        //EXECUTE
        Mockito.when(accountInputService.getAccountsByCustomer(CUSTOMER_ID)).thenReturn(accounts);
        List<Account> accounts1 = underTest.getAccountByCustomerId(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(accountInputService, Mockito.atLeast(1)).getAccountsByCustomer(CUSTOMER_ID);
            Assertions.assertNotNull(accounts1);
            Assertions.assertEquals(accounts, accounts1);
        });
    }

    @Test
    void create() throws RemoteCustomerApiStatusInvalidException, AccountTypeInvalidException, RemoteCustomerApiException, AccountFieldsInvalidException {
        //PREPARE
        //EXECUTE
        Mockito.when(accountInputService.createAccount(dto)).thenReturn(account);
        Account account1 = underTest.create(dto);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(account1);
            Assertions.assertEquals(account,account1);
            Mockito.verify(accountInputService, Mockito.atLeast(1)).createAccount(dto);
        });
    }
    @Test
    void getAll() {
        // PREPARE
        List<Account> accounts = List.of(account);
        //EXECUTE
        Mockito.when(accountInputService.getAllAccounts()).thenReturn(accounts);
        List<Account> accounts1 = underTest.getAll();
        // VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(accounts1);
            Assertions.assertEquals(accounts,accounts1);
            Mockito.verify(accountInputService, Mockito.atLeast(1)).getAllAccounts();
        });
    }

    @Test
    void get() throws AccountNotFoundException {
        //PREPARE
        //EXECUTE
        Mockito.when(accountInputService.getAccountById(ACCOUNT_ID)).thenReturn(account);
        Account account1 = underTest.get(ACCOUNT_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(account1);
            Assertions.assertEquals(account,account1);
            Mockito.verify(accountInputService, Mockito.atLeast(1)).getAccountById(ACCOUNT_ID);
        });
    }

    @Test
    void delete() throws RemoteCustomerApiException, AccountNotFoundException {
        //PREPARE
        //EXECUTE
        Mockito.when(accountInputService.deleteAccount(ACCOUNT_ID)).thenReturn("deleted");
        String expected = underTest.delete(ACCOUNT_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(accountInputService, Mockito.atLeast(1)).deleteAccount(ACCOUNT_ID);
            Assertions.assertNotNull(expected);
        });
    }

    @Test
    void update() throws RemoteCustomerApiStatusInvalidException, AccountTypeInvalidException, RemoteCustomerApiException, AccountNotFoundException, AccountFieldsInvalidException {
        //PREPARE
        AccountDto dto1 = AccountDto.builder().type("saving").balance(20000).build();
        Account updated = account;
        updated.setBalance(200100);
        //EXECUTE
        Mockito.when(accountInputService.update(dto1, ACCOUNT_ID)).thenReturn(updated);
        Account account1 = underTest.update(dto1, ACCOUNT_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(accountInputService, Mockito.atLeast(1)).update(dto1,ACCOUNT_ID);
            Assertions.assertNotNull(account1);
            Assertions.assertEquals(updated.getBalance(),account1.getBalance());
        });
    }
}