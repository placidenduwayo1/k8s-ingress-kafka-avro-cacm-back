package fr.placide.cacmerbsmsmovement.domain.inputport;

import fr.placide.cacmerbsmsmovement.domain.beans.Movement;
import fr.placide.cacmerbsmsmovement.domain.exceptions.business_exc.*;
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.models.MovementDto;

import java.util.List;

public interface MovementInputService {
    Movement createMvt(MovementDto dto) throws RemoteAccountApiUnreachableException, RemoteAccountBalanceNotEnoughException,
            RemoteSavingAccountCannotUndergoMovementException, MovementFieldsInvalidException, RemoteCustomerApiUnreachable,
            MovementSensInvalidException, RemoteCustomerStatusUnauthorizedException;
    List<Movement> getAll();
    Movement getMovementById(String mvtId) throws MovementNotFoundException, RemoteCustomerApiUnreachable, RemoteAccountApiUnreachableException;
    Movement updateMovement(MovementDto dto, String mvtId) throws MovementNotFoundException, RemoteAccountApiUnreachableException,
            RemoteSavingAccountCannotUndergoMovementException, MovementFieldsInvalidException, RemoteCustomerApiUnreachable,
            MovementSensInvalidException, RemoteCustomerStatusUnauthorizedException;
    String deleteMovement(String mvtId) throws MovementNotFoundException, RemoteCustomerApiUnreachable, RemoteAccountApiUnreachableException,
            MovementAssignedAccountException;
    List<Movement> getOperationsByCustomerName(String lastname) throws RemoteCustomerApiUnreachable, MovementNotFoundException, RemoteCustomerApiNotFoundException;
}
