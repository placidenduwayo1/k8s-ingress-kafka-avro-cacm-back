package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.fallbacks;

import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies.RiskEvaluatorServiceProxy;
import org.springframework.stereotype.Service;

@Service
public class RiskEvaluatorFallback implements RiskEvaluatorServiceProxy {

    @Override
    public double getRemoteRiskEvaluatorToEvaluate(String id, String movementSens, double movementAmount) {
        return 0;
    }
}