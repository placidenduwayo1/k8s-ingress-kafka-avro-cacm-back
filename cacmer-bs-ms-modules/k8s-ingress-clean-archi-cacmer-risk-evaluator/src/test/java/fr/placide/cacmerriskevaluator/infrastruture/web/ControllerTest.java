package fr.placide.cacmerriskevaluator.infrastruture.web;

import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteAccountApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteCustomerApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.inputport.InputPortRiskEvaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

class ControllerTest {
    @Mock
    private InputPortRiskEvaluator inputPortRiskEvaluator;
    @InjectMocks
    private Controller underTest;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWelcome() {
        //PREPARE
        Map<String, String> message = Map.of("risk-eval-svc",
                "welcome to service of risk evaluation of customers and their accounts against movements");
        //EXECUTE
        Map<String, String> msg = underTest.getWelcome();
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(msg);
            Assertions.assertEquals(message, msg);
        });
    }

    @Test
    void evaluate() throws RemoteCustomerApiUnreachableException, RemoteAccountApiUnreachableException {
        //PREPARE
        String accountId="id";
        String mvtSens ="buy";
        double mvtAmount =50;
        //EXECUTE
        Mockito.when(inputPortRiskEvaluator.evaluate(accountId,mvtSens,mvtAmount)).thenReturn(50.0);
        double evaluation = underTest.evaluate(accountId,mvtSens,mvtAmount);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(inputPortRiskEvaluator, Mockito.atLeast(1)).evaluate(accountId,mvtSens,mvtAmount);
        });
    }
}