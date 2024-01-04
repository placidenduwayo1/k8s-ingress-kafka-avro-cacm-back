package fr.placide.cacmerriskevaluator.infrastruture.controller;

import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteAccountApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteCustomerApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.usecase.InputPortRiskEvaluator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/risk-evaluator")
public class Controller {
    private final InputPortRiskEvaluator inputPortRiskEvaluator;
    @Value("${message}")
    private String[] message;

    public Controller(InputPortRiskEvaluator inputPortRiskEvaluator) {
        this.inputPortRiskEvaluator = inputPortRiskEvaluator;
    }

    @GetMapping(value = "")

    public Map<String, String> getWelcome() {
        return Map.of(message[0], message[1]);
    }

    @GetMapping(value = "/{id}/{movementSens}/{movementAmount}")
    public double evaluate(@PathVariable(name = "id") String id, @PathVariable(name = "movementSens") String movementSens,
                           @PathVariable(name = "movementAmount") double movementAmount) throws RemoteAccountApiUnreachableException,
            RemoteCustomerApiUnreachableException {
        return inputPortRiskEvaluator.evaluate(id, movementSens, movementAmount);
    }
}
