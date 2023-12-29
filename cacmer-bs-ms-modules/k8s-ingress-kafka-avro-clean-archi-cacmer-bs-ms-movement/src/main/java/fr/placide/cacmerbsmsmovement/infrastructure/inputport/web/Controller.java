package fr.placide.cacmerbsmsmovement.infrastructure.inputport.web;

import fr.placide.cacmerbsmsmovement.domain.beans.Movement;
import fr.placide.cacmerbsmsmovement.domain.exceptions.business_exc.*;
import fr.placide.cacmerbsmsmovement.domain.inputport.MovementInputService;
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.models.MovementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/bs-ms-mvt")
public class Controller {
    @Value("${welcome}")
    private String [] welcome;

    private final MovementInputService inputService;
    @GetMapping(value = "")
    public Map<String, Object> getWelcome(){
        return Map.of(welcome[0], welcome[1]);
    }
    @GetMapping(value = "/movements")
    public List<Movement> getAllMovements(){
        return inputService.getAll();
    }
    @GetMapping(value = "/movements/id/{id}")
    public Movement getMovementById(@PathVariable(name = "id") String id) throws MovementNotFoundException,
            RemoteCustomerApiUnreachable, RemoteAccountApiUnreachableException {
        return inputService.getMovementById(id);
    }
    @PostMapping(value = "/movements")
    public Movement create(@RequestBody MovementDto dto) throws RemoteSavingAccountCannotUndergoMovementException,
            RemoteAccountBalanceNotEnoughException, MovementFieldsInvalidException, RemoteCustomerApiUnreachable,
            RemoteAccountApiUnreachableException, MovementSensInvalidException, RemoteCustomerStatusUnauthorizedException {
        return inputService.createMvt(dto);
    }
    @DeleteMapping(value = "/movements/id/{id}")
    public String delete(@PathVariable(name = "id")String id) throws MovementAssignedAccountException, MovementNotFoundException,
            RemoteCustomerApiUnreachable, RemoteAccountApiUnreachableException {
        return inputService.deleteMovement(id);
    }
    @PutMapping(value = "/movements/id/{id}")
    public Movement update(@RequestBody MovementDto dto, @PathVariable(name = "id")String id) throws RemoteSavingAccountCannotUndergoMovementException,
            MovementNotFoundException, MovementFieldsInvalidException, RemoteCustomerApiUnreachable, RemoteAccountApiUnreachableException,
            MovementSensInvalidException, RemoteCustomerStatusUnauthorizedException {
        return inputService.updateMovement(dto,id);
    }
    @GetMapping(value = "/movements/customers/name/{lastname}")
    public List<Movement> getOperationsByCustomerName(@PathVariable(name = "lastname") String lastname) throws RemoteCustomerApiUnreachable,
            MovementNotFoundException {
        return inputService.getOperationsByCustomerName(lastname);
    }
}
