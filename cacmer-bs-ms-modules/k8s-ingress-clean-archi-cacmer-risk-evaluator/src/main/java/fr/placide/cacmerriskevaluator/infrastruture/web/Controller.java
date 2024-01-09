package fr.placide.cacmerriskevaluator.infrastruture.web;

import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteAccountApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteCustomerApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.inputport.InputPortRiskEvaluator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/risk-evaluator")
public class Controller {
    private final InputPortRiskEvaluator inputPortRiskEvaluator;
    private static final  Map<String, String> MESSAGE = Map.of("risk-eval-svc",
            "welcome to service of risk evaluation of customers and their accounts against movements");

    public Controller(InputPortRiskEvaluator inputPortRiskEvaluator) {
        this.inputPortRiskEvaluator = inputPortRiskEvaluator;
    }

    @GetMapping(value = "")

    public Map<String, String> getWelcome() {
        return MESSAGE;
    }

    @GetMapping(value = "/{id}/{movementSens}/{movementAmount}")
    public double evaluate(@PathVariable(name = "id") String id, @PathVariable(name = "movementSens") String movementSens,
                           @PathVariable(name = "movementAmount") double movementAmount) throws RemoteAccountApiUnreachableException,
            RemoteCustomerApiUnreachableException {
        return inputPortRiskEvaluator.evaluate(id, movementSens, movementAmount);
    }
}
