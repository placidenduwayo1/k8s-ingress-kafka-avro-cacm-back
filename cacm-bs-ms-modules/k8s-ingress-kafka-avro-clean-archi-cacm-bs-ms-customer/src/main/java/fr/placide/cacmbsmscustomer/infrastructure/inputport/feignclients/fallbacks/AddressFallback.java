package fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.fallbacks;

import fr.placide.cacmbsmscustomer.domain.exceptions.ExceptionMsg;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AddressModel;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.proxies.AddressServiceProxy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AddressFallback implements AddressServiceProxy {
    @Override
    public AddressModel getRemoteAddressById(String addressId) {
        return AddressModel.builder()
                .addressId(ExceptionMsg.REMOTE_ADDRESS_API.getMsg())
                .num(0)
                .street(ExceptionMsg.REMOTE_ADDRESS_API.getMsg())
                .pb(0)
                .city(ExceptionMsg.REMOTE_ADDRESS_API.getMsg())
                .country(ExceptionMsg.REMOTE_ADDRESS_API.getMsg())
                .build();
    }
    @Override
    public List<AddressModel> getRemoteAddressesByCity(String city) {
        return Collections.emptyList();
    }
}
