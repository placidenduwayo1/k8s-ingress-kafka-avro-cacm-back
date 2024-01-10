package fr.placide.cacmerriskevaluator.infrastruture.feignclients.fallbacks;

import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.CustomerModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CustomerFallbackTest {
    private static final CustomerFallback UNDER_TEST = new CustomerFallback();

    @Test
    void getRemoteCustomerById() {
        String customerId = "id";
        CustomerModel model = UNDER_TEST.getRemoteCustomerById(customerId);
        Assertions.assertNull(model);
    }
}