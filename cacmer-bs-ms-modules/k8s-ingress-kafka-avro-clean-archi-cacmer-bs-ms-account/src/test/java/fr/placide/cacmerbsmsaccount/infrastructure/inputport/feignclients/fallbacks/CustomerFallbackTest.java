package fr.placide.cacmerbsmsaccount.infrastructure.inputport.feignclients.fallbacks;

import fr.placide.cacmerbsmsaccount.infrastructure.inputport.feignclients.models.CustomerModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class CustomerFallbackTest {
    private static final CustomerFallback UNDER_TEST=new CustomerFallback();
    @Test
    void getRemoteCustomer() {
        //PREPARE
        String customerId ="id";
        //EXECUTE
        CustomerModel model = UNDER_TEST.getRemoteCustomer(customerId);
        // VERIFY
        Assertions.assertNull(model);
    }

    @Test
    void getRemoteCustomersByName() {
        //PREPARE
        String name = "lastname";
        //EXECUTE
        List<CustomerModel> models = UNDER_TEST.getRemoteCustomersByName(name);
        //VERIFY
        Assertions.assertEquals(Collections.emptyList(),models);
    }
}