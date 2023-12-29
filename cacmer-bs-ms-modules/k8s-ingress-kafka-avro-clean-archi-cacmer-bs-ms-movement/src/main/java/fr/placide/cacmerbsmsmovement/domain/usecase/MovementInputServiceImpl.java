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
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.models.MovementDto;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class MovementInputServiceImpl implements MovementInputService {
    private final RemoteAccountService remoteAccountService;
    private final RemoteCustomerService remoteCustomerService;
    private final MovementProducerService producerService;
    private final MovementOutputService outputService;

    public MovementInputServiceImpl(RemoteAccountService remoteAccountService,
                                    RemoteCustomerService remoteCustomerService, MovementProducerService producerService, MovementOutputService outputService) {
        this.remoteAccountService = remoteAccountService;
        this.remoteCustomerService = remoteCustomerService;
        this.producerService = producerService;
        this.outputService = outputService;
    }

    private void validateMovementFields(MovementDto dto) throws MovementFieldsInvalidException, MovementSensInvalidException,
            RemoteAccountApiUnreachableException, RemoteSavingAccountCannotUndergoMovementException, RemoteCustomerApiUnreachable {
        if (!MovementValidationTools.isValidMvt(dto)) {
            throw new MovementFieldsInvalidException();
        } else if (!MovementValidationTools.isValidSens(dto.getSens())) {
            throw new MovementSensInvalidException();
        }
        Account account = remoteAccountService.getRemoteAccountById(dto.getAccountId());//check account reachable
        if (account.getType().equals("saving")) {
            throw new RemoteSavingAccountCannotUndergoMovementException(); //check account type
        }
        Customer customer = remoteCustomerService.getRemoteCustomerById(dto.getCustomerId());///check customer reachable
        if (!customer.getCustomerId().equals(account.getCustomerId())) {
            throw new RemoteCustomerApiUnreachable();
        }
    }

    private void setDependencies(Movement movement, String accountId, String customerId) throws RemoteAccountApiUnreachableException,
            RemoteCustomerApiUnreachable {
        Account account = remoteAccountService.getRemoteAccountById(accountId);
        Customer customer = remoteCustomerService.getRemoteCustomerById(customerId);
        movement.setAccount(account);
        movement.setCustomer(customer);
    }

    @Override
    public Movement createMvt(MovementDto dto) throws RemoteAccountApiUnreachableException, RemoteSavingAccountCannotUndergoMovementException,
            MovementFieldsInvalidException, RemoteCustomerApiUnreachable, MovementSensInvalidException {

        MovementValidationTools.format(dto);
        validateMovementFields(dto);

        Movement movement = Mapper.map(dto);
        movement.setMvtId(UUID.randomUUID().toString());
        movement.setCreatedAt(Timestamp.from(Instant.now()).toString());
        setDependencies(movement, dto.getAccountId(), dto.getCustomerId());

        MovementAvro avro = Mapper.toAvro(movement);
        MovementAvro produced = producerService.produceKafkaEventCreateMovement(avro);
        outputService.createMvt(Mapper.map(produced));
        return Mapper.map(produced);
    }

    @Override
    public List<Movement> getAll() {
        List<Movement> movements = outputService.getAll();
        if (!movements.isEmpty()) {
            movements.forEach(mvt -> {
                try {
                    setDependencies(mvt, mvt.getAccountId(), mvt.getCustomerId());
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
            setDependencies(mvt, mvt.getAccountId(), mvt.getCustomerId());
        } else {
            throw new MovementNotFoundException();
        }
        return mvt;
    }

    @Override
    public Movement updateMovement(MovementDto dto, String mvtId) throws MovementNotFoundException, RemoteAccountApiUnreachableException,
            RemoteSavingAccountCannotUndergoMovementException, MovementFieldsInvalidException, RemoteCustomerApiUnreachable, MovementSensInvalidException {
        validateMovementFields(dto);
        Movement mvt = getMovementById(mvtId);
        mvt.setSens(dto.getSens());
        mvt.setAmount(dto.getAmount());
        setDependencies(mvt, mvt.getAccountId(), mvt.getCustomerId());
        MovementAvro produced = producerService.produceKafkaEventUpdateMovement(Mapper.toAvro(mvt));
        outputService.updateMovement(Mapper.map(produced));
        return Mapper.map(produced);
    }

    @Override
    public String deleteMovement(String mvtId) throws MovementNotFoundException, RemoteCustomerApiUnreachable, RemoteAccountApiUnreachableException,
            MovementAssignedAccountException {
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
}
