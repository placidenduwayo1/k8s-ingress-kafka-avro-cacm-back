package fr.placide.cacmerbsmsaccount.domain.inputport;

import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.*;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountDto;

import java.util.List;

public interface AccountInputService {
    Account createAccount(AccountDto dto) throws AccountFieldsInvalidException, RemoteCustomerApiException, AccountTypeInvalidException, RemoteCustomerApiStatusInvalidException, AccountAlreadyExistsException;
    List<Account> getAllAccounts();
    Account update(AccountDto dto, String accountId) throws AccountNotFoundException, RemoteCustomerApiException,
            AccountFieldsInvalidException, AccountTypeInvalidException, RemoteCustomerApiStatusInvalidException;
    String deleteAccount(String id) throws AccountNotFoundException;

    Account getAccountById(String id) throws AccountNotFoundException;
    Account getAccountByCustomer(String customerId);
    List<Account> getAccountsByCustomersName(String customerLastname) throws RemoteCustomerApiException;
}
