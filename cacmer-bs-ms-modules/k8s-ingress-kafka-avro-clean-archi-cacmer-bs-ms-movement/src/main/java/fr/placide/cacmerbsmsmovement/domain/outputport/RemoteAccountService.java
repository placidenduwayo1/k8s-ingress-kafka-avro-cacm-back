package fr.placide.cacmerbsmsmovement.domain.outputport;

import fr.placide.cacmerbsmsmovement.domain.beans.Account;
import fr.placide.cacmerbsmsmovement.domain.exceptions.business_exc.RemoteAccountApiUnreachableException;

import java.util.List;

public interface RemoteAccountService {
    Account getRemoteAccountById(String id) throws RemoteAccountApiUnreachableException;
    List<Account> getRemoteAccountsByCustomerId(String id);
}
