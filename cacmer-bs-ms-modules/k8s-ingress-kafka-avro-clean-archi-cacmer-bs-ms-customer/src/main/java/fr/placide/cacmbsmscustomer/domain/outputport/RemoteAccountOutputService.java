package fr.placide.cacmbsmscustomer.domain.outputport;


import fr.placide.cacmbsmscustomer.domain.beans.Account;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.RemoteAccountApiException;

import java.util.List;

public interface RemoteAccountOutputService {
    List<Account> getRemoteAccountsByCustomerId(String id) throws RemoteAccountApiException;
}
