package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.fallbacks;

import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.CustomerModel;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies.CustomerServiceProxy;
import org.springframework.stereotype.Service;

@Service

public class CustomerFallback implements CustomerServiceProxy {
    @Override
    public CustomerModel getRemoteCustomer(String id) {
        return null;
    }
}
