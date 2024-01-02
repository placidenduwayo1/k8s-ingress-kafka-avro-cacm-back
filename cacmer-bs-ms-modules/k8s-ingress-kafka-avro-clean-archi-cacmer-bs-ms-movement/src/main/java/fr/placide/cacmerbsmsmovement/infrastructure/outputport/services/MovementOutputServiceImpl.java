package fr.placide.cacmerbsmsmovement.infrastructure.outputport.services;

import fr.placide.cacmerbsmsmovement.domain.avro.MovementAvro;
import fr.placide.cacmerbsmsmovement.domain.beans.Account;
import fr.placide.cacmerbsmsmovement.domain.beans.Customer;
import fr.placide.cacmerbsmsmovement.domain.beans.Movement;
import fr.placide.cacmerbsmsmovement.domain.exceptions.business_exc.MovementNotFoundException;
import fr.placide.cacmerbsmsmovement.domain.outputport.MovementConsumerService;
import fr.placide.cacmerbsmsmovement.domain.outputport.MovementOutputService;
import fr.placide.cacmerbsmsmovement.domain.outputport.RemoteAccountService;
import fr.placide.cacmerbsmsmovement.domain.outputport.RemoteCustomerService;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.AccountDto;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.AccountModel;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.CustomerModel;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies.AccountServiceProxy;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies.CustomerServiceProxy;
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.repository.MovementRepo;
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
public class MovementOutputServiceImpl implements MovementConsumerService, MovementOutputService,
        RemoteAccountService, RemoteCustomerService {
    private final AccountServiceProxy accountServiceProxy;
    private final CustomerServiceProxy customerServiceProxy;
    private final MovementRepo movementRepo;

    public MovementOutputServiceImpl(@Qualifier(value = "accountserviceproxy") AccountServiceProxy accountServiceProxy,
                                     @Qualifier(value = "customerserviceproxy") CustomerServiceProxy customerServiceProxy,
                                     MovementRepo movementRepo) {
        this.accountServiceProxy = accountServiceProxy;
        this.customerServiceProxy = customerServiceProxy;
        this.movementRepo = movementRepo;
    }

    @Override
    @KafkaListener(topics = "movement-created", groupId = "movement")
    public Movement consumeMovementCreateEvent(@Payload MovementAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        return Mapper.map(avro);
    }

    @Override
    @KafkaListener(topics = "movement-edited", groupId = "movement")
    public Movement consumeMovementEditEvent(@Payload MovementAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
       return Mapper.map(avro);
    }

    @Override
    @KafkaListener(topics = "movement-deleted", groupId = "movement")
    public Movement consumeMovementDeleteEvent(@Payload MovementAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Movement bean = Mapper.map(avro);
        log.info("movement to delete:<{}> consumed from topic:<{}>", bean, topic);
        return bean;
    }

    @Override
    public void createMvt(Movement movement) {
        MovementAvro avro = Mapper.toAvro(movement);
        Movement bean = consumeMovementCreateEvent(avro, "movement-created");
        log.info("movement to create:<{}> consumed from topic:<{}>", bean, "movement-created");
        movementRepo.save(Mapper.map(bean));
    }
    @Override
    public void updateMovement(Movement movement) {
        MovementAvro avro = Mapper.toAvro(movement);
        Movement bean = consumeMovementEditEvent(avro,"movement-edited");
        log.info("movement to update:<{}> consumed from topic:<{}>", bean, "movement-edited");
        movementRepo.save(Mapper.map(bean));
    }

    @Override
    public void deleteMovement(Movement movement) {
        MovementAvro avro = Mapper.toAvro(movement);
        Movement consumed = consumeMovementDeleteEvent(avro,"movement-deleted");
        log.info("movement to update:<{}> consumed from topic:<{}>", consumed, "movement-deleted");
        movementRepo.deleteById(consumed.getMvtId());
    }

    @Override
    public List<Movement> getMovementsByAccountId(String accountId) {
        return movementRepo.findByAccountId(accountId).stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public List<Movement> getAll() {
        return movementRepo.findAll().stream().map(Mapper::map).toList();
    }

    @Override
    public Movement getMovementById(String mvtId) throws MovementNotFoundException {
        return Mapper.map(movementRepo.findById(mvtId).orElseThrow(MovementNotFoundException::new));
    }

    @Override
    public Account getRemoteAccountById(String id) {
        AccountModel model = accountServiceProxy.getRemoteAccountById(id);
        Account account = null;
        if (model != null) {
            account = Mapper.map(model);
        }
        return account;
    }

    @Override
    public List<Account> getRemoteAccountsByCustomerId(String id) {
        List<AccountModel> models = accountServiceProxy.getRemoteAccountsByCustomerId(id);
        List<Account> accounts = new ArrayList<>();
        if(!models.isEmpty()){
            accounts = models.stream().map(Mapper::map).toList();
        }
        return accounts;
    }

    @Override
    public void updateAccountAfterOperation(AccountDto accountDto, String accountId) {
        accountServiceProxy.updateAccountAfterOperation(accountDto,accountId);
    }

    @Override
    public Customer getRemoteCustomerById(String id) {
        CustomerModel model = customerServiceProxy.getRemoteCustomer(id);
        Customer bean = null;
        if (model != null) {
            bean = Mapper.map(model);
        }
        return bean;
    }

    @Override
    public List<Customer> getRemoteCustomersByName(String lastname) {
        List<CustomerModel> models= customerServiceProxy.getRemoteCustomersByName(lastname);
        List<Customer> beans = new ArrayList<>();
        if(!models.isEmpty()){
            beans = models.stream().map(Mapper::map).toList();
        }
        return beans;
    }
}
