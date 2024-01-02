package fr.placide.cacmerriskevaluator.infrastruture.controller;

import fr.placide.cacmerriskevaluator.domain.inputport.RiskEvaluator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/risk-evaluator")
public class Controller {
    private final RiskEvaluator riskEvaluator;
    @Value("${message}")
    private String[] message;

    public Controller(RiskEvaluator riskEvaluator) {
        this.riskEvaluator = riskEvaluator;
    }

    @GetMapping(value = "")

    public Map<String, String> getWelcome() {
        return Map.of(message[0], message[1]);
    }

    @GetMapping(value = "/evaluate/{mvtSens}/{mvtAmount}/{customerRisk}/{accountBalance}/{accountOverdraft}")
    public double getEvaluation(@PathVariable(name = "mvtSens") String mvtSens,
                                @PathVariable(name = "mvtAmount") double mvtAmount,
                                @PathVariable(name = "customerRisk") String customerRisk,
                                @PathVariable(name = "accountBalance") double accountBalance,
                                @PathVariable(name = "accountOverdraft") double accountOverdraft) {
        return riskEvaluator.evaluate(mvtSens, mvtAmount, customerRisk, accountBalance, accountOverdraft);
    }
}
