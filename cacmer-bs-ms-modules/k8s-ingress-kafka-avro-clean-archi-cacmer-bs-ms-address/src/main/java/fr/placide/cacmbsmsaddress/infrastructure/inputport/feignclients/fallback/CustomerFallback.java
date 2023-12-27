package fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.fallback;

import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.model.CustomerModel;
import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.proxy.CustomerServiceProxy;
import java.util.Collections;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFallback implements CustomerServiceProxy {
    @Override
    public List<CustomerModel> getRemoteCustomersByAddress(String customerId) {
        return Collections.emptyList();
    }
}
