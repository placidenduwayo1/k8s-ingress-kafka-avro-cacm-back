package fr.placide.cacmerriskevaluator.infrastruture.service;

import fr.placide.cacmerriskevaluator.domain.beans.Account;
import fr.placide.cacmerriskevaluator.domain.beans.Customer;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.AccountModel;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.CustomerModel;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.proxies.AccountServiceProxy;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.proxies.CustomerServiceProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class RemoteObjectsGetterImplTest {
    @Mock
    private AccountServiceProxy accountServiceProxy;
    @Mock
    private CustomerServiceProxy customerServiceProxy;
    @InjectMocks
    private RemoteObjectsGetterImpl underTest;
    private static final String ACCOUNT_ID="id";
    private Account account;
    private static final String CUSTOMER_ID="id";
    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account.AccountBuilder()
                .accountId(ACCOUNT_ID)
                .balance(100)
                .type("current")
                .overdraft(100)
                .customerId(CUSTOMER_ID)
                .build();
        customer = new Customer.CustomerBuilder()
                .customerId(CUSTOMER_ID)
                .firstname("first")
                .lastname("last")
                .createdAt("now")
                .risk("low")
                .status("active")
                .build();
    }

    @Test
    void getRemoteObjects() {
        //PREPARE
        AccountModel model1 = AccountModel.builder()
                .accountId(ACCOUNT_ID)
                .type(account.getType())
                .balance(account.getBalance())
                .overdraft(account.getOverdraft())
                .customerId(account.getCustomerId())
                .build();
        CustomerModel model2 = CustomerModel.builder()
                .customerId(CUSTOMER_ID)
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .createdAt(customer.getCreatedAt())
                .risk(customer.getRisk())
                .status(customer.getStatus())
                .build();
        //EXECUTE
        Mockito.when(accountServiceProxy.getRemoteAccountById(ACCOUNT_ID)).thenReturn(model1);
        Mockito.when(customerServiceProxy.getRemoteCustomerById(CUSTOMER_ID)).thenReturn(model2);
        Account account = underTest.getRemoteAccountById(CUSTOMER_ID);
        Customer customer = underTest.getRemoteCustomerById(CUSTOMER_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(accountServiceProxy, Mockito.atLeast(1)).getRemoteAccountById(ACCOUNT_ID);
            Mockito.verify(customerServiceProxy, Mockito.atLeast(1)).getRemoteCustomerById(CUSTOMER_ID);
            Assertions.assertNotNull(account);
            Assertions.assertNotNull(customer);
        });
    }
}