package fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.fallbacks;

import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AccountModel;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.proxies.AccountServiceProxy;
import org.springframework.stereotype.Service;

@Service
public class AccountFallback implements AccountServiceProxy {

    @Override
    public AccountModel getRemoteAccountByCustomerId(String id) {
        return null;
    }
}
