package fr.placide.cacmbsmscustomer.domain.outputport;


import fr.placide.cacmbsmscustomer.domain.beans.Account;

public interface RemoteAccountOutputService {
    Account getRemoteAccountByCustomerId(String id);
}
