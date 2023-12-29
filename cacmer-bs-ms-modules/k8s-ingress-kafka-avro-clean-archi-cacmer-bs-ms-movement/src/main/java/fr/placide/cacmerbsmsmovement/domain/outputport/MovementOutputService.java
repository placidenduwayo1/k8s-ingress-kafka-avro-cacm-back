package fr.placide.cacmerbsmsmovement.domain.outputport;

import fr.placide.cacmerbsmsmovement.domain.beans.Movement;
import fr.placide.cacmerbsmsmovement.domain.exceptions.business_exc.MovementNotFoundException;

import java.util.List;

public interface MovementOutputService {
    void createMvt(Movement movement);
    List<Movement> getAll();
    Movement getMovementById(String mvtId) throws MovementNotFoundException;
    void updateMovement(Movement movement);
    void deleteMovement(Movement movement) throws MovementNotFoundException;
    List<Movement> getMovementsByAccountId(String accountId) throws MovementNotFoundException;

}
