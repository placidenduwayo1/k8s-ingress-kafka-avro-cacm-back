package fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.fallbacks;

import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountFallbackTest {
private AccountFallback underTest;
    @BeforeEach
    void setUp() {
        underTest = new AccountFallback();
    }

    @Test
    void getRemoteAccountsByCustomerId() {
        //PREPARE
        String customerId="customer-id";
        //EXECUTE
        List<AccountModel> models = underTest.getRemoteAccountsByCustomerId(customerId);
        //VERIFY
        Assertions.assertEquals(Collections.emptyList(),models);
    }
}