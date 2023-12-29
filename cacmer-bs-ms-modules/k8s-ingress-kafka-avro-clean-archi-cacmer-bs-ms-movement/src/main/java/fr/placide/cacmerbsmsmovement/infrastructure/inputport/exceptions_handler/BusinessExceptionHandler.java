package fr.placide.cacmerbsmsmovement.infrastructure.inputport.exceptions_handler;

import fr.placide.cacmerbsmsmovement.domain.exceptions.ExceptionMsg;
import fr.placide.cacmerbsmsmovement.domain.exceptions.business_exc.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(value = RemoteAccountApiUnreachableException.class)
    public ResponseEntity<String> handleRemoteAccountApiUnreachableException(){
        return new ResponseEntity<>(ExceptionMsg.REMOTE_ACCOUNT_API.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = RemoteSavingAccountCannotUndergoMovementException.class)
    public ResponseEntity<String> handleAccountTypeException(){
        return new ResponseEntity<>(ExceptionMsg.REMOTE_ACCOUNT_API_TYPE.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = MovementFieldsInvalidException.class)
    public ResponseEntity<String> handleMovementFieldsInvalidException(){
        return new ResponseEntity<>(ExceptionMsg.MVT_FIELDS_INVALID.getMsg(),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = RemoteCustomerApiUnreachable.class)
    public ResponseEntity<String> handleRemoteCustomerApiUnreachable(){
        return new ResponseEntity<>(ExceptionMsg.REMOTE_CUSTOMER_API.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = MovementAssignedAccountException.class)
    public ResponseEntity<String> handleMovementAssignedAccountException(){
        return new ResponseEntity<>(ExceptionMsg.MVT_ASSIGNED_ACCOUNT.getMsg(),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = MovementNotFoundException.class)
    public ResponseEntity<String> handleMovementNotFoundException(){
        return new ResponseEntity<>(ExceptionMsg.MVT_NOT_FOUND.getMsg(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = MovementSensInvalidException.class)
    public ResponseEntity<String> handleMovementSensInvalidException(){
        return new ResponseEntity<>(ExceptionMsg.MVT_SENS.getMsg(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = RemoteCustomerApiNotFoundException.class)
    public ResponseEntity<String> handleRemoteCustomerApiNotFoundException(){
        return new ResponseEntity<>(ExceptionMsg.REMOTE_CUSTOMER_NOT_FOUND.getMsg(), HttpStatus.NOT_FOUND);
    }
}
