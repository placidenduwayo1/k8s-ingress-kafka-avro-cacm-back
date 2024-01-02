package fr.placide.cacmerbsmsmovement.domain.outputport;

import fr.placide.cacmerbsmsmovement.domain.beans.Account;
import fr.placide.cacmerbsmsmovement.domain.exceptions.business_exc.RemoteAccountApiUnreachableException;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.AccountDto;

import java.util.List;

public interface RemoteAccountService {
    Account getRemoteAccountById(String id) throws RemoteAccountApiUnreachableException;
    List<Account> getRemoteAccountsByCustomerId(String id);
    void updateAccountAfterOperation(AccountDto accountDto, String id);
}
