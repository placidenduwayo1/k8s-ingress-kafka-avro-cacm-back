package fr.placide.cacmerbsmsaccount.domain.usecase;

import fr.placide.cacmerbsmsaccount.domain.avro.AccountAvro;
import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.*;
import fr.placide.cacmerbsmsaccount.domain.inputport.AccountInputService;
import fr.placide.cacmerbsmsaccount.domain.inputport.RemoteCustomerInputService;
import fr.placide.cacmerbsmsaccount.domain.outputport.AccountOutputService;
import fr.placide.cacmerbsmsaccount.domain.outputport.AccountProducerService;
import fr.placide.cacmerbsmsaccount.domain.outputport.RemoteCustomerOutputService;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountInputServiceImpl implements AccountInputService, RemoteCustomerInputService {
    private final RemoteCustomerOutputService remoteCustomerOutputService;
    private final AccountOutputService accountOutputService;
    private final AccountProducerService accountProducerService;

    public AccountInputServiceImpl(RemoteCustomerOutputService remoteCustomerOutputService, AccountOutputService accountOutputService, AccountProducerService accountProducerService) {
        this.remoteCustomerOutputService = remoteCustomerOutputService;
        this.accountOutputService = accountOutputService;
        this.accountProducerService = accountProducerService;
    }

    private void validateAccountFields(AccountDto dto) throws AccountFieldsInvalidException, AccountTypeInvalidException,
            RemoteCustomerApiException, RemoteCustomerApiStatusInvalidException {
        if(!AccountValidationTools.isValidAccount(dto)){
            throw new AccountFieldsInvalidException();
        } else if (!AccountValidationTools.isValidType(dto.getType())) {
            throw new AccountTypeInvalidException();
        } else if (!AccountValidationTools.isValidBalance(dto.getBalance())) {
            throw new AccountFieldsInvalidException();
        } else if (!AccountValidationTools.isValidOverDraft(dto.getOverdraft())) {
            throw new AccountFieldsInvalidException();
        }

        Customer customer = getRemoteCustomerById(dto.getCustomerId());
        if(!AccountValidationTools.isValidRemoteCustomerStatus(customer.getStatus())){
            throw new RemoteCustomerApiStatusInvalidException();
        }
    }
    private void validateAccountExists(String customerId) throws AccountAlreadyExistsException {
        Account account = getAccountByCustomer(customerId);
        if(account!=null){
            throw new AccountAlreadyExistsException();
        }
    }
    private void setDependency(Account account, String customerId) throws RemoteCustomerApiException {
        Customer customer = getRemoteCustomerById(customerId);
        account.setCustomerId(customerId);
        account.setCustomer(customer);
    }
    @Override
    public Account createAccount(AccountDto dto) throws AccountFieldsInvalidException, RemoteCustomerApiException, AccountTypeInvalidException,
            RemoteCustomerApiStatusInvalidException, AccountAlreadyExistsException {
        AccountValidationTools.format(dto);
        validateAccountFields(dto);
        validateAccountExists(dto.getCustomerId());
        Account account = Mapper.map(dto);
        account.setAccountId(UUID.randomUUID().toString());
        setDependency(account, dto.getCustomerId());
        AccountAvro avro = Mapper.toAvro(account);
        AccountAvro produced = accountProducerService.produceKafkaEventCreateAccount(avro);
        accountOutputService.create(Mapper.map(produced));
        return Mapper.map(produced);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountOutputService.getAll();
    }

    @Override
    public Account update(AccountDto dto, String accountId) throws AccountNotFoundException, RemoteCustomerApiException,
            AccountFieldsInvalidException, AccountTypeInvalidException, RemoteCustomerApiStatusInvalidException {
       AccountValidationTools.format(dto);
       validateAccountFields(dto);
       Account account = getAccountById(accountId);
       account.setType(dto.getType());
       account.setBalance(dto.getBalance());
       account.setOverdraft(dto.getOverdraft());
       account.setCustomerId(dto.getCustomerId());
       setDependency(account,account.getCustomerId());
       AccountAvro produced = accountProducerService.produceKafkaEventUpdateAccount(Mapper.toAvro(account));
       accountOutputService.update(Mapper.map(produced));
       return Mapper.map(produced);
    }

    @Override
    public String deleteAccount(String id) throws AccountNotFoundException {
        Account account = getAccountById(id);
        AccountAvro avro = Mapper.toAvro(account);
        AccountAvro produced = accountProducerService.produceKafkaEventDeleteAccount(avro);
        accountOutputService.delete(Mapper.map(produced));
        return "account: " + produced + " deleted";
    }

    @Override
    public Account getAccountById(String id) throws AccountNotFoundException {
        return accountOutputService.get(id);
    }

    @Override
    public Account getAccountByCustomer(String customerId){
        return accountOutputService.getAccountByCustomer(customerId);
    }

    @Override
    public List<Account> getAccountsByCustomersName(String customerLastname) throws RemoteCustomerApiException {
        List<Customer> customers = getRemoteCustomersByName(customerLastname);
        List<Account> accounts = new ArrayList<>();
        for(Customer customer: customers){
           Account account = getAccountByCustomer(customer.getCustomerId());
           if(account!=null){
               setDependency(account, account.getCustomerId());
           }
           accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Customer getRemoteCustomerById(String id) throws RemoteCustomerApiException {
       Customer customer = remoteCustomerOutputService.getRemoteCustomerById(id);
       if(customer==null){
           throw new RemoteCustomerApiException();
       }
       return customer;
    }

    @Override
    public List<Customer> getRemoteCustomersByName(String lastname) throws RemoteCustomerApiException {
        List<Customer> customers = remoteCustomerOutputService.getCustomersByName(lastname);
        if(customers.isEmpty()){
            throw new RemoteCustomerApiException();
        }
        return customers;
    }
}
