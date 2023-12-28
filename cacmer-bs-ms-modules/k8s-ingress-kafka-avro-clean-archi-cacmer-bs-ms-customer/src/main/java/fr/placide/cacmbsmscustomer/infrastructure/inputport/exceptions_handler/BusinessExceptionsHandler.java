package fr.placide.cacmbsmscustomer.infrastructure.inputport.exceptions_handler;

import fr.placide.cacmbsmscustomer.domain.exceptions.ExceptionMsg;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessExceptionsHandler {
    @ExceptionHandler(value = CustomerAlreadyExistsException.class)
    public ResponseEntity<String> handleCustomerAlreadyExistsException(){
        return new ResponseEntity<>(ExceptionMsg.CUSTOMER_EXISTS.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = CustomerFieldsInvalidException.class)
    public ResponseEntity<String> handleCustomerFieldsInvalidException(){
        return new ResponseEntity<>(ExceptionMsg.FIELDS_INVALID.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(){
        return new ResponseEntity<>(ExceptionMsg.CUSTOMER_NOT_FOUND.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = CustomerRiskInvalidException.class)
    public ResponseEntity<String> handleCustomerRiskInvalidException(){
        return new ResponseEntity<>(ExceptionMsg.RISK_INVALID.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = CustomerStatusInvalidException.class)
    public ResponseEntity<String> handleCustomerStatusInvalidException(){
        return new ResponseEntity<>(ExceptionMsg.STATUS_INVALID.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = RemoteAddressApiException.class)
    public ResponseEntity<String> handleRemoteAddressApiException(){
        return new ResponseEntity<>(ExceptionMsg.REMOTE_ADDRESS_API.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = CustomerAssignedAccountException.class)
    public ResponseEntity<String> handleCustomerAssignedAccountException(){
        return new ResponseEntity<>(ExceptionMsg.CUSTOMER_ASSIGNED_ACCOUNT.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
}
