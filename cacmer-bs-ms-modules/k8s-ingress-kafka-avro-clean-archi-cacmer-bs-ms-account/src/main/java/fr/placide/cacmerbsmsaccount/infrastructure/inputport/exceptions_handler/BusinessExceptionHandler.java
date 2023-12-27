package fr.placide.cacmerbsmsaccount.infrastructure.inputport.exceptions_handler;

import fr.placide.cacmerbsmsaccount.domain.exceptions.ExceptionMsg;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(value = AccountAlreadyExistsException.class )
    public ResponseEntity<String> handleAccountAlreadyExistsException(){
        return new ResponseEntity<>(ExceptionMsg.ACCOUNT_EXISTS.getMsg(),
                HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = AccountFieldsInvalidException.class)
    public ResponseEntity<String> handleAccountFieldsInvalidException(){
        return new ResponseEntity<>(ExceptionMsg.ACCOUNT_FIELDS.getMsg(),
                HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(){
        return new ResponseEntity<>(ExceptionMsg.ACCOUNT_NOT_FOUND.getMsg(),
                HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = AccountTypeInvalidException.class)
    public ResponseEntity<String> handleAccountTypeInvalidException(){
        return new ResponseEntity<>(ExceptionMsg.ACCOUNT_TYPE.getMsg(),
                HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = RemoteCustomerApiException.class )
    public ResponseEntity<String> handleRemoteCustomerApiException(){
        return new ResponseEntity<>(ExceptionMsg.REMOTE_CUSTOMER_UNREACHABLE.getMsg(),
                HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = RemoteCustomerApiStatusInvalidException.class)
    public ResponseEntity<String> handleRemoteCustomerApiStatusInvalidException(){
        return new ResponseEntity<>(ExceptionMsg.REMOTE_CUSTOMER_STATUS.getMsg(),
                HttpStatus.NOT_ACCEPTABLE);
    }
}
