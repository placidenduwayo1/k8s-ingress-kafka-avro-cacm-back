package fr.placide.cacmerbsmsaccount.infrastructure.inputport.exceptions_handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class BusinessExceptionHandlerTest {
    private static final BusinessExceptionHandler UNDER_TEST=
            new BusinessExceptionHandler();

    @Test
    void handleAccountFieldsInvalidException() {
        ResponseEntity<String> response = UNDER_TEST.handleAccountFieldsInvalidException();
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(response);
            Assertions.assertEquals(406,response.getStatusCode().value());
        });
    }

    @Test
    void handleAccountNotFoundException() {
        //EXECUTE
        ResponseEntity<String> response = UNDER_TEST.handleAccountNotFoundException();
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(response);
            Assertions.assertEquals(406,response.getStatusCode().value());
        });
    }

    @Test
    void handleAccountTypeInvalidException() {
        ResponseEntity<String> response = UNDER_TEST.handleAccountTypeInvalidException();
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(response);
            Assertions.assertEquals(406,response.getStatusCode().value());
        });
    }

    @Test
    void handleRemoteCustomerApiException() {
        ResponseEntity<String> response = UNDER_TEST.handleRemoteCustomerApiException();
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(response);
            Assertions.assertEquals(406,response.getStatusCode().value());
        });
    }

    @Test
    void handleRemoteCustomerApiStatusInvalidException() {
        ResponseEntity<String> response = UNDER_TEST.handleRemoteCustomerApiStatusInvalidException();
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(response);
            Assertions.assertEquals(406,response.getStatusCode().value());
        });
    }
}