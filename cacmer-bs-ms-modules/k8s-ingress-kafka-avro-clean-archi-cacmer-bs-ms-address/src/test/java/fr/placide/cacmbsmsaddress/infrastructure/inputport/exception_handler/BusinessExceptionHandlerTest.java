package fr.placide.cacmbsmsaddress.infrastructure.inputport.exception_handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;


class BusinessExceptionHandlerTest {
    private BusinessExceptionHandler underTest;
    @BeforeEach
    void setUp() {
        underTest = new BusinessExceptionHandler();
    }

    @Test
    void handleAddressAlreadyExistsException() {
        ResponseEntity<String> responseEntity = underTest.handleAddressAlreadyExistsException();
        Assertions.assertEquals(406,responseEntity.getStatusCode().value());
    }

    @Test
    void handleAddressFieldsInvalidException() {
        ResponseEntity<String> responseEntity = underTest.handleAddressFieldsInvalidException();
        Assertions.assertEquals(406,responseEntity.getStatusCode().value());
    }

    @Test
    void handleAddressNotFoundException() {
        ResponseEntity<String> responseEntity = underTest.handleAddressNotFoundException();
        Assertions.assertEquals(404,responseEntity.getStatusCode().value());
    }

    @Test
    void handleRemoteCustomerAPIException() {
        ResponseEntity<String> responseEntity = underTest.handleRemoteCustomerAPIException();
        Assertions.assertEquals(406,responseEntity.getStatusCode().value());
    }

    @Test
    void handleAddressAssignedCustomerException() {
        ResponseEntity<String> responseEntity = underTest.handleAddressAssignedCustomerException();
        Assertions.assertEquals(406,responseEntity.getStatusCode().value());
    }
}