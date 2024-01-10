package fr.placide.cacmerriskevaluator.infrastruture.exception_handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class BusinessExceptionHandlerTest {
    private BusinessExceptionHandler underTest;
    @BeforeEach
    void setUp(){
        underTest = new BusinessExceptionHandler();
    }

    @Test
    void handleRemoteAccountUnreachableException() {
        //EXECUTE
        ResponseEntity<String> responseEntity = underTest.handleRemoteAccountUnreachableException();
        //VERIFY
        Assertions.assertAll("",()-> Assertions.assertEquals(404,responseEntity.getStatusCode().value()));
    }

    @Test
    void handleRemoteCustomerUnreachableException() {
        //EXECUTE
        ResponseEntity<String> responseEntity = underTest.handleRemoteCustomerUnreachableException();
        //VERIFY
        Assertions.assertAll("",()-> Assertions.assertEquals(404,responseEntity.getStatusCode().value()));
    }
}