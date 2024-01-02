package fr.placide.cacmerriskevaluator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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

    public Map<String, String> getWelcome(){
        return Map.of(message[0], message[1]);
    }
    @GetMapping(value = "/evaluate")
    public boolean getEvaluation(String mvtSens, double mvtAmount, String customerRisk, double balance, double overdraft){
        return riskEvaluator.evaluate(mvtSens,mvtAmount,customerRisk,balance,overdraft);
    }
}
