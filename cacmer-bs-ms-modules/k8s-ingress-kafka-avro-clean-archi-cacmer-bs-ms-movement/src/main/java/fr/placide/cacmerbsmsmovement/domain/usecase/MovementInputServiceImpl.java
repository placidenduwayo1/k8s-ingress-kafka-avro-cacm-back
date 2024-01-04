package fr.placide.cacmerbsmsmovement.domain.usecase;

import fr.placide.cacmerbsmsmovement.domain.avro.MovementAvro;
import fr.placide.cacmerbsmsmovement.domain.beans.Account;
import fr.placide.cacmerbsmsmovement.domain.beans.Customer;
import fr.placide.cacmerbsmsmovement.domain.beans.Movement;
import fr.placide.cacmerbsmsmovement.domain.exceptions.business_exc.*;
import fr.placide.cacmerbsmsmovement.domain.inputport.MovementInputService;
import fr.placide.cacmerbsmsmovement.domain.outputport.MovementOutputService;
import fr.placide.cacmerbsmsmovement.domain.outputport.MovementProducerService;
import fr.placide.cacmerbsmsmovement.domain.outputport.RemoteAccountService;
import fr.placide.cacmerbsmsmovement.domain.outputport.RemoteCustomerService;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.AccountDto;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies.RiskEvaluatorServiceProxy;
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.models.MovementDto;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Slf4j
public class MovementInputServiceImpl implements MovementInputService {
    private final RemoteAccountService remoteAccountService;
    private final RemoteCustomerService remoteCustomerService;
    private final MovementProducerService producerService;
    private final MovementOutputService outputService;
    private final RiskEvaluatorServiceProxy riskEvaluatorServiceProxy;
    public MovementInputServiceImpl(RemoteAccountService remoteAccountService,
                                    RemoteCustomerService remoteCustomerService, MovementProducerService producerService,
                                    MovementOutputService outputService, RiskEvaluatorServiceProxy riskEvaluatorServiceProxy) {
        this.remoteAccountService = remoteAccountService;
        this.remoteCustomerService = remoteCustomerService;
        this.producerService = producerService;
        this.outputService = outputService;
        this.riskEvaluatorServiceProxy = riskEvaluatorServiceProxy;
    }

    private void validateMovementFields(MovementDto dto) throws MovementFieldsInvalidException, MovementSensInvalidException,
            RemoteAccountApiUnreachableException, RemoteSavingAccountCannotUndergoMovementException, RemoteCustomerApiUnreachable,
            RemoteCustomerStatusUnauthorizedException {
        if (!MovementValidationTools.isValidMvt(dto)) {
            throw new MovementFieldsInvalidException();
        } else if (!MovementValidationTools.isValidSens(dto.getSens())) {
            throw new MovementSensInvalidException();
        }
        //check account reachable
        Account account = remoteAccountService.getRemoteAccountById(dto.getAccountId());
        if (account == null) {
            throw new RemoteAccountApiUnreachableException();
        } else if (account.getType().equals("saving")) {
            throw new RemoteSavingAccountCannotUndergoMovementException(); //check account type
        }
        //check customer reachable
        Customer customer = remoteCustomerService.getRemoteCustomerById(account.getCustomerId());
        if (customer == null) {
            throw new RemoteCustomerApiUnreachable();
        } else if (customer.getStatus().equals("archive")) {
            throw new RemoteCustomerStatusUnauthorizedException();
        }
    }

    private void setDependencies(Movement movement) throws RemoteAccountApiUnreachableException,
            RemoteCustomerApiUnreachable {
        Account account = remoteAccountService.getRemoteAccountById(movement.getAccountId());
        Customer customer = remoteCustomerService.getRemoteCustomerById(account.getCustomerId());
        account.setCustomer(customer);
        movement.setAccount(account);
    }

    @Override
    public Movement createMvt(MovementDto dto) throws RemoteAccountApiUnreachableException,
            RemoteSavingAccountCannotUndergoMovementException, MovementFieldsInvalidException, RemoteCustomerApiUnreachable,
            MovementSensInvalidException, RemoteCustomerStatusUnauthorizedException, RemoteAccountBalanceNotEnoughException {

        MovementValidationTools.format(dto);
        validateMovementFields(dto);

        Movement movement = Mapper.map(dto);
        movement.setMvtId(UUID.randomUUID().toString());
        movement.setCreatedAt(Timestamp.from(Instant.now()).toString());
        setDependencies(movement);
        Account account = remoteAccountService.getRemoteAccountById(dto.getAccountId());
        double balance = riskEvaluatorServiceProxy.getRemoteRiskEvaluatorToEvaluate(account.getAccountId(),
                movement.getSens(), movement.getAmount());
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! {}",balance);
        if(balance-50< movement.getAmount()){
            throw new RemoteAccountBalanceNotEnoughException();
        }
        MovementAvro avro = Mapper.toAvro(movement);
        MovementAvro produced = producerService.produceKafkaEventCreateMovement(avro);
        outputService.createMvt(Mapper.map(produced));
        account.setBalance(account.getBalance()- movement.getAmount());
        AccountDto accountDto = Mapper.map(account);
       remoteAccountService.updateAccountAfterOperation(accountDto, account.getAccountId());

        return Mapper.map(produced);
    }

    @Override
    public List<Movement> getAll() {
        List<Movement> movements = outputService.getAll();
        if (!movements.isEmpty()) {
            movements.forEach(mvt -> {
                try {
                    setDependencies(mvt);
                } catch (RemoteAccountApiUnreachableException | RemoteCustomerApiUnreachable e) {
                    e.getMessage();
                }
            });
        }
        return movements;
    }

    @Override
    public Movement getMovementById(String mvtId) throws MovementNotFoundException, RemoteCustomerApiUnreachable,
            RemoteAccountApiUnreachableException {
        Movement mvt = outputService.getMovementById(mvtId);
        if (mvt != null) {
            setDependencies(mvt);
        } else {
            throw new MovementNotFoundException();
        }
        return mvt;
    }

    @Override
    public Movement updateMovement(MovementDto dto, String mvtId) throws MovementNotFoundException, RemoteAccountApiUnreachableException,
            RemoteSavingAccountCannotUndergoMovementException, MovementFieldsInvalidException, RemoteCustomerApiUnreachable, MovementSensInvalidException,
            RemoteCustomerStatusUnauthorizedException {
        MovementValidationTools.format(dto);
        validateMovementFields(dto);
        Movement mvt = getMovementById(mvtId);
        mvt.setSens(dto.getSens());
        mvt.setAmount(dto.getAmount());
        setDependencies(mvt);
        MovementAvro produced = producerService.produceKafkaEventUpdateMovement(Mapper.toAvro(mvt));
        outputService.updateMovement(Mapper.map(produced));
        return Mapper.map(produced);
    }

    @Override
    public String deleteMovement(String mvtId) throws MovementNotFoundException, RemoteCustomerApiUnreachable,
            RemoteAccountApiUnreachableException, MovementAssignedAccountException {
        Movement mvt = getMovementById(mvtId);
        Account account = remoteAccountService.getRemoteAccountById(mvt.getAccountId());
        if (account != null) {
            throw new MovementAssignedAccountException();
        }
        MovementAvro avro = Mapper.toAvro(mvt);
        MovementAvro produced = producerService.produceKafkaEventDeleteMovement(avro);
        outputService.deleteMovement(Mapper.map(avro));

        return "movement: " + produced + " deleted";
    }

    @Override
    public List<Movement> getOperationsByCustomerName(String lastname) throws  RemoteCustomerApiNotFoundException {
        List<Customer> customers = remoteCustomerService.getRemoteCustomersByName(lastname);
        if(customers.isEmpty()){
            throw new RemoteCustomerApiNotFoundException();
        }
        return getMvt(customers);
    }

    private List<Movement> getMvt(List<Customer> customers) {
        List<Movement> movements = new ArrayList<>();
        for(Customer customer: customers){
            List<Account> accounts = remoteAccountService.getRemoteAccountsByCustomerId(customer.getCustomerId());
            if(!accounts.isEmpty()){
                accounts.forEach(account -> {
                    try {
                        List<Movement> subMvt = outputService.getMovementsByAccountId(account.getAccountId());
                        if(!subMvt.isEmpty()){
                            subMvt.forEach(movement -> {
                                try {
                                    setDependencies(movement);
                                    movements.add(movement);
                                } catch (RemoteAccountApiUnreachableException | RemoteCustomerApiUnreachable e) {
                                    e.getMessage();
                                }
                            });
                        }
                    } catch (MovementNotFoundException e) {
                        e.getMessage();
                    }
                });
            }
        }
        return movements;
    }
}
