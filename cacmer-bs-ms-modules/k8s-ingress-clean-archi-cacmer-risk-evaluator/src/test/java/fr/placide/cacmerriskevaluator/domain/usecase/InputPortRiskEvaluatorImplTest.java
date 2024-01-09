package fr.placide.cacmerriskevaluator.domain.usecase;

import fr.placide.cacmerriskevaluator.domain.beans.Account;
import fr.placide.cacmerriskevaluator.domain.beans.Customer;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteAccountApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteCustomerApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.outputport.RemoteObjectsGetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


class InputPortRiskEvaluatorImplTest {
    @Mock
    private RemoteObjectsGetter remoteObjectsGetter;
    @InjectMocks
    private InputPortRiskEvaluatorImpl underTest;
    private Account account;
    private static final String ACCOUNT_ID="id";
    private Customer customer;
    private static final String CUSTOMER_ID="id";
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
    void evaluate() throws RemoteCustomerApiUnreachableException, RemoteAccountApiUnreachableException {
        //PREPARE
        String mvtSens ="buy";
        double mvtAmount =50;
        //EXECUTE
        Mockito.when(remoteObjectsGetter.getRemoteAccountById(ACCOUNT_ID)).thenReturn(account);
        Mockito.when(remoteObjectsGetter.getRemoteCustomerById(CUSTOMER_ID)).thenReturn(customer);
        double balance1 = underTest.evaluate(ACCOUNT_ID,mvtSens,mvtAmount);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(remoteObjectsGetter, Mockito.atLeast(1)).getRemoteAccountById(ACCOUNT_ID);
            Mockito.verify(remoteObjectsGetter, Mockito.atLeast(1)).getRemoteCustomerById(CUSTOMER_ID);
            Assertions.assertEquals(account.getBalance()-50,balance1);
        });
    }
}