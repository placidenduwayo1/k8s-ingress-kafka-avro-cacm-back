package fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.fallback;

import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.model.CustomerModel;
import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.proxy.CustomerServiceProxy;
import org.springframework.stereotype.Service;

@Service
public class CustomerFallback implements CustomerServiceProxy {
    @Override
    public CustomerModel getCustomerId(String customerId) {
        return null;
    }
}
