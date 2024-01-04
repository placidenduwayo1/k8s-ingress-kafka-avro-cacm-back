package fr.placide.cacmerriskevaluator.infrastruture.feignclients.fallbacks;

import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.CustomerModel;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.proxies.CustomerServiceProxy;
import org.springframework.stereotype.Service;

@Service
public class CustomerFallback implements CustomerServiceProxy {
    @Override
    public CustomerModel getRemoteCustomerById(String id) {
        return null;
    }
}
