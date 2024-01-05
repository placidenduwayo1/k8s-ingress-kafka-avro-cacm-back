package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.fallbacks;

import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.AccountDto;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.AccountModel;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies.AccountServiceProxy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AccountFallback implements AccountServiceProxy {
    @Override
    public AccountModel getRemoteAccountById(String id) {
        return null;
    }

    @Override
    public List<AccountModel> getRemoteAccountsByCustomerId(String id) {
        return Collections.emptyList();
    }

    @Override
    public AccountModel updateAccountAfterOperation(AccountDto accountDto, String id) {
        return null;
    }
}
