package fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.fallbacks;

import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AddressModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class AddressFallbackTest {
    private AddressFallback underTest;
    @BeforeEach
    void setUp() {
        underTest = new AddressFallback();
    }

    @Test
    void getRemoteAddressById() {
        //PREPARE
        String addressId="address-id";
        String expected ="Remote Address Api Unreachable Exception, maybe api is down";
        //EXECUTE
        AddressModel model = underTest.getRemoteAddressById(addressId);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertEquals(0, model.getNum());
            Assertions.assertEquals(expected,model.getAddressId());
        });
    }

    @Test
    void getRemoteAddressesByCity() {
        //PREPARE
        String city ="city";
        //EXECUTE
        List<AddressModel> models = underTest.getRemoteAddressesByCity(city);
        //VERIFY
        Assertions.assertEquals(Collections.emptyList(),models);
    }
}