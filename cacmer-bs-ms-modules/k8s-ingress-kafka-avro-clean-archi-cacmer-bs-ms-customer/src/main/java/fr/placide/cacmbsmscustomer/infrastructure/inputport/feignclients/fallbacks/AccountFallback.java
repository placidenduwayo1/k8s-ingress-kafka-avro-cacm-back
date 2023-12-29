package fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.fallbacks;

import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AccountModel;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.proxies.AccountServiceProxy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AccountFallback implements AccountServiceProxy {

    @Override
    public List<AccountModel> getRemoteAccountsByCustomerId(String id) {
        return Collections.emptyList();
    }
}
