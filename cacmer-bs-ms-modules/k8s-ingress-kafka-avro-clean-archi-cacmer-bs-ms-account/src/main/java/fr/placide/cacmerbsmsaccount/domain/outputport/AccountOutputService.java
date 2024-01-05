package fr.placide.cacmerbsmsaccount.domain.outputport;


import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.AccountNotFoundException;

import java.util.List;

public interface AccountOutputService {
    void create(Account account);
    List<Account> getAll();
    Account get(String id) throws AccountNotFoundException;
    void update(Account account);
    void delete(String accountId) throws AccountNotFoundException;
    List<Account> getAccountsByCustomer(String customerId);
}
