package fr.placide.cacmbsmscustomer.infrastructure.inputport.exceptions_handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class BusinessExceptionsHandlerTest {
    private BusinessExceptionsHandler underTest;
    private static final int CODE=406;

    @BeforeEach
    void setUp() {
        underTest = new BusinessExceptionsHandler();
    }

    @Test
    void handleCustomerAlreadyExistsException() {
        //execute
        ResponseEntity<String> response = underTest.handleCustomerAlreadyExistsException();
        //verify
        Assertions.assertEquals(CODE,response.getStatusCode().value());
    }

    @Test
    void handleCustomerFieldsInvalidException() {
        //execute
        ResponseEntity<String> response = underTest.handleCustomerFieldsInvalidException();
        //verify
        Assertions.assertEquals(CODE,response.getStatusCode().value());
    }

    @Test
    void handleCustomerNotFoundException() {
        //execute
        ResponseEntity<String> response = underTest.handleCustomerNotFoundException();
        //verify
        Assertions.assertEquals(404,response.getStatusCode().value());
    }

    @Test
    void handleCustomerRiskInvalidException() {
        //execute
        ResponseEntity<String> response = underTest.handleCustomerRiskInvalidException();
        //verify
        Assertions.assertEquals(CODE,response.getStatusCode().value());
    }

    @Test
    void handleCustomerStatusInvalidException() {
        //execute
        ResponseEntity<String> response = underTest.handleCustomerStatusInvalidException();
        //verify
        Assertions.assertEquals(CODE,response.getStatusCode().value());
    }

    @Test
    void handleRemoteAddressApiException() {
        //execute
        ResponseEntity<String> response = underTest.handleRemoteAddressApiException();
        //verify
        Assertions.assertEquals(CODE,response.getStatusCode().value());
    }

    @Test
    void handleCustomerAssignedAccountException() {
        //execute
        ResponseEntity<String> response = underTest.handleCustomerAssignedAccountException();
        //verify
        Assertions.assertEquals(CODE,response.getStatusCode().value());
    }

    @Test
    void handleRemoteAccountApiException() {
        //execute
        ResponseEntity<String> response = underTest.handleRemoteAccountApiException();
        //verify
        Assertions.assertEquals(CODE,response.getStatusCode().value());
    }
}