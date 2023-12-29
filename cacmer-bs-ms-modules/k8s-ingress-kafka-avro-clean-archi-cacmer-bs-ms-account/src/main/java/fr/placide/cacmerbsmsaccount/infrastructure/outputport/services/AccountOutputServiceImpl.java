package fr.placide.cacmerbsmsaccount.infrastructure.outputport.services;

import fr.placide.cacmerbsmsaccount.domain.avro.AccountAvro;
import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.AccountNotFoundException;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.RemoteCustomerApiException;
import fr.placide.cacmerbsmsaccount.domain.outputport.AccountConsumerService;
import fr.placide.cacmerbsmsaccount.domain.outputport.AccountOutputService;
import fr.placide.cacmerbsmsaccount.domain.outputport.RemoteCustomerOutputService;
import fr.placide.cacmerbsmsaccount.infrastructure.inputport.feignclients.models.CustomerModel;
import fr.placide.cacmerbsmsaccount.infrastructure.inputport.feignclients.proxies.CustomerServiceProxy;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountModel;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.repository.AccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class AccountOutputServiceImpl implements AccountConsumerService, AccountOutputService, RemoteCustomerOutputService {
    private final AccountRepo accountRepo;
    private final CustomerServiceProxy serviceProxy;
    public AccountOutputServiceImpl(AccountRepo accountRepo,@Qualifier(value = "customerserviceproxy")CustomerServiceProxy serviceProxy) {
        this.accountRepo = accountRepo;
        this.serviceProxy = serviceProxy;
    }

    @Override
    @KafkaListener(topics = "account-created",groupId = "account")
    public Account consumeAccountCreateEvent(@Payload AccountAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Account account = Mapper.map(avro);
        log.info("account to create:<{}> consumed from topic:<{}>", account, topic);
        return account;
    }

    @Override
    public Account consumeAccountEditEvent(@Payload AccountAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Account account = Mapper.map(avro);
        log.info("account to update:<{}> consumed from topic:<{}>", account, topic);
        return account;
    }

    @Override
    public Account consumeAccountDeleteEvent(@Payload AccountAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Account account = Mapper.map(avro);
        log.info("account to delete:<{}> consumed from topic:<{}>", account, topic);
        return account;
    }

    @Override
    public void create(Account account) {
        AccountAvro avro = Mapper.toAvro(account);
        Account consumed= consumeAccountCreateEvent(avro,"account-created");
        accountRepo.save(Mapper.map(consumed));
    }

    @Override
    public List<Account> getAll() {
        return map(accountRepo.findAll());
    }

    @Override
    public Account get(String id) throws AccountNotFoundException {
        return map(accountRepo.findById(id).orElseThrow(AccountNotFoundException::new));
    }

    @Override
    public void update(Account account) {
        AccountAvro avro = Mapper.toAvro(account);
        Account consumed= consumeAccountEditEvent(avro,"account-edited");
        accountRepo.save(Mapper.map(consumed));
    }

    @Override
    public void delete(Account account) {
        AccountAvro avro = Mapper.toAvro(account);
        Account consumed= consumeAccountCreateEvent(avro,"account-deleted");
        accountRepo.deleteById(consumed.getAccountId());
    }

    @Override
    public List<Account> getAccountsByCustomer(String customerId){
        List<AccountModel> models  = accountRepo.findByCustomerId(customerId);
        List<Account> accounts = new ArrayList<>();
        if(!models.isEmpty()){
            accounts = map(models);
        }
        return accounts;
    }

    @Override
    public Customer getRemoteCustomerById(String id) throws RemoteCustomerApiException {
        CustomerModel model = serviceProxy.getRemoteCustomer(id);
        Customer bean = null;
        if(model!=null){
            bean = Mapper.map(model);
        }
        return bean;
    }

    @Override
    public List<Customer> getCustomersByName(String lastname) {
        List<CustomerModel> models = serviceProxy.getRemoteCustomersByName(lastname);
        List<Customer> customers = new ArrayList<>();
        if(!models.isEmpty()){
            customers = models.stream()
                    .map(Mapper::map)
                    .toList();
        }
        return customers;
    }

    private List<Account> map(List<AccountModel> models){
        return models.stream().map(Mapper::map).toList();
    }
    private Account map(AccountModel model){
        return Mapper.map(model);
    }
}
