package fr.placide.cacmerbsmsaccount.infrastructure.inputport.web;

import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
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

class ControllerTest {
    @Mock
    private AccountInputService accountInputService;
    @InjectMocks
    private Controller underTest;
    private static final String CUSTOMER_ID="id";
    private static final String ACCOUNT_ID="id";
    private Customer customer;
    private Account account;
    private AccountDto dto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = Customer.builder().customerId(CUSTOMER_ID).firstname("placide")
                .lastname("nduwayo").createdAt("now").risk("low").status("active").build();
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
        account.setAccountId(ACCOUNT_ID);
        List<Account> accounts = List.of(account);
        //EXECUTE
        Mockito.when(accountInputService.getAccountsByCustomer(CUSTOMER_ID)).thenReturn(accounts);
        List<Account> accounts1 = underTest.getAccountByCustomerId(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(accountInputService, Mockito.atLeast(1)).getAccountsByCustomer(CUSTOMER_ID);
            Assertions.assertEquals(accounts.size(),accounts1.size());
        });
    }

    @Test
    void getAccountsByCustomersName() {
    }

    @Test
    void getAccountByCustomerId() {
    }

    @Test
    void create() {
    }

    @Test
    void getAll() {
    }

    @Test
    void get() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}