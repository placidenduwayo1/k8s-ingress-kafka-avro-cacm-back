package fr.placide.cacmerriskevaluator.infrastruture.exception_handler;

import fr.placide.cacmerriskevaluator.domain.exceptions.ExceptionMsg;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteAccountApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteCustomerApiUnreachableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(value = RemoteAccountApiUnreachableException.class)
    public ResponseEntity<String> handleRemoteAccountUnreachableException(){
        return new ResponseEntity<>(ExceptionMsg.REMOTE_ACCOUNT_API.getMsg(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = RemoteCustomerApiUnreachableException.class)
    public ResponseEntity<String> handleRemoteCustomerUnreachableException(){
        return new ResponseEntity<>(ExceptionMsg.REMOTE_CUSTOMER_API.getMsg(), HttpStatus.NOT_FOUND);
    }
}
