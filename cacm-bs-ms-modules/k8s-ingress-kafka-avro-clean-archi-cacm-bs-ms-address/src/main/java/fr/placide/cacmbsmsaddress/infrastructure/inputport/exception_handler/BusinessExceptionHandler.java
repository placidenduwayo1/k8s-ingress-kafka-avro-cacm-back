package fr.placide.cacmbsmsaddress.infrastructure.inputport.exception_handler;

import fr.placide.cacmbsmsaddress.domain.exceptions.ExceptionMsg;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(value = AddressAlreadyExistsException.class)
    public ResponseEntity<String> handleAddressAlreadyExistsException(){
        return new ResponseEntity<>(ExceptionMsg.ADDRESS_EXIST.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = AddressFieldsInvalidException.class)
    public ResponseEntity<String> handleAddressFieldsInvalidException(){
        return new ResponseEntity<>(ExceptionMsg.FIELDS_INVALID.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = AddressNotFoundException.class)
    public ResponseEntity<String> handleAddressNotFoundException(){
        return new ResponseEntity<>(ExceptionMsg.NOT_FOUND.getMsg(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = RemoteCustomerAPIException.class)
    public ResponseEntity<String> handleRemoteCustomerAPIException(){
        return new ResponseEntity<>(ExceptionMsg.REMOTE_CUSTOMER_ERROR.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = AddressAssignedCustomerException.class)
    public ResponseEntity<String> handleAddressAssignedCustomerException(){
        return new ResponseEntity<>(ExceptionMsg.ADDRESS_ASSIGNED_CUSTOMER.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }
}
