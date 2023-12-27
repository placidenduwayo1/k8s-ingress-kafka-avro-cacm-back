package fr.placide.cacmerbsmsaccount.infrastructure.inputport.feignclients.fallbacks;
import fr.placide.cacmerbsmsaccount.infrastructure.inputport.feignclients.models.CustomerModel;
import fr.placide.cacmerbsmsaccount.infrastructure.inputport.feignclients.proxies.CustomerServiceProxy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerFallback implements CustomerServiceProxy {
    @Override
    public CustomerModel getRemoteCustomer(String id) {
        return null;
    }

    @Override
    public List<CustomerModel> getRemoteCustomersByName(String lastname) {
        return Collections.emptyList();
    }
}
