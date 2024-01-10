package fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.fallback;

import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.model.CustomerModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerFallbackTest {
    private CustomerFallback underTest;
    @BeforeEach
    void setUp(){
        underTest = new CustomerFallback();
    }

    @Test
    void getRemoteCustomersByAddress() {
        String addressId ="address-id";
        List<CustomerModel> emptyList = Collections.emptyList();
        List<CustomerModel> models = underTest.getRemoteCustomersByAddress(addressId);
        Assertions.assertAll("",()-> Assertions.assertEquals(emptyList,models));
    }
}