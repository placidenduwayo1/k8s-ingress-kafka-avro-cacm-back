package fr.placide.cacmerriskevaluator.infrastruture.feignclients.fallbacks;

import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.AccountModel;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.proxies.AccountServiceProxy;
import org.springframework.stereotype.Service;

@Service
public class AccountFallback implements AccountServiceProxy {
    @Override
    public AccountModel getRemoteAccountById(String id) {
        return null;
    }
}
