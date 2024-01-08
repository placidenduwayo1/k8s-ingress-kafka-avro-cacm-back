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
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
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
        if (!AccountValidationTools.isValidAccount(dto)) {
            throw new AccountFieldsInvalidException();
        } else if (!AccountValidationTools.isValidType(dto.getType())) {
            throw new AccountTypeInvalidException();
        } else if (!AccountValidationTools.isValidOverDraft(dto.getOverdraft())) {
            throw new AccountFieldsInvalidException();
        }

        Customer customer = getRemoteCustomerById(dto.getCustomerId());
        if (!AccountValidationTools.isValidRemoteCustomerStatus(customer.getStatus())) {
            throw new RemoteCustomerApiStatusInvalidException();
        }
    }

    private void setDependency(Account account) throws RemoteCustomerApiException {
        Customer customer = getRemoteCustomerById(account.getCustomerId());
        account.setCustomerId(customer.getCustomerId());
        account.setCustomer(customer);
    }

    @Override
    public Account createAccount(AccountDto dto) throws AccountFieldsInvalidException, RemoteCustomerApiException,
            AccountTypeInvalidException, RemoteCustomerApiStatusInvalidException {
        AccountValidationTools.format(dto);
        validateAccountFields(dto);
        Account account = Mapper.map(dto);
        account.setAccountId(UUID.randomUUID().toString());
        setDependency(account);
        AccountAvro avro = Mapper.toAvro(account);
        AccountAvro avro1 = accountProducerService.produceKafkaEventCreateAccount(avro);
        accountOutputService.create(Mapper.map(avro1));
        return Mapper.map(avro1);
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = accountOutputService.getAll();
        accounts.forEach(account -> {
            try {
                setDependency(account);
            } catch (RemoteCustomerApiException e) {
                e.getMessage();
            }
        });
        return accounts;
    }

    @Override
    public Account update(AccountDto dto, String accountId) throws AccountNotFoundException, RemoteCustomerApiException,
            AccountFieldsInvalidException, AccountTypeInvalidException, RemoteCustomerApiStatusInvalidException {
        AccountValidationTools.format(dto);
        validateAccountFields(dto);
        Account account = getAccountById(accountId);
        account.setType(dto.getType());
        account.setBalance(account.getBalance() + dto.getBalance());
        account.setOverdraft(dto.getOverdraft());
        account.setCustomerId(account.getCustomerId());
        setDependency(account);
        AccountAvro produced = accountProducerService.produceKafkaEventUpdateAccount(Mapper.toAvro(account));
        accountOutputService.update(Mapper.map(produced));
        return Mapper.map(produced);
    }

    @Override
    public String deleteAccount(String id) throws AccountNotFoundException, RemoteCustomerApiException {
        Account account = getAccountById(id);
        setDependency(account);
        AccountAvro avro = Mapper.toAvro(account);
        AccountAvro produced = accountProducerService.produceKafkaEventDeleteAccount(avro);
        accountOutputService.delete(id);
        return "account: " + produced + " deleted";
    }

    @Override
    public Account getAccountById(String id) throws AccountNotFoundException {
        return accountOutputService.get(id);
    }

    @Override
    public List<Account> getAccountsByCustomer(String customerId) {
        return accountOutputService.getAccountsByCustomer(customerId);
    }
    @Override
    public List<Account> getAccountsByCustomersName(String customerLastname) throws RemoteCustomerApiException {
        List<Customer> customers = getRemoteCustomersByName(customerLastname);
        List<Account> accounts = new ArrayList<>();
        for (Customer customer : customers) {
            List<Account> subList = getAccountsByCustomer(customer.getCustomerId());
            if (!subList.isEmpty()) {
                for (Account account : subList) {
                    setDependency(account);
                    accounts.add(account);
                }
            }
        }
        return accounts;
    }

    @Override
    public Customer getRemoteCustomerById(String id) throws RemoteCustomerApiException {
        Customer customer = remoteCustomerOutputService.getRemoteCustomerById(id);
        if (customer == null) {
            throw new RemoteCustomerApiException();
        }
        return customer;
    }

    @Override
    public List<Customer> getRemoteCustomersByName(String lastname) throws RemoteCustomerApiException {
        List<Customer> customers = remoteCustomerOutputService.getCustomersByName(lastname);
        if (customers.isEmpty()) {
            throw new RemoteCustomerApiException();
        }
        return customers;
    }
}
