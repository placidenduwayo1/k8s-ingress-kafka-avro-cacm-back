package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.fallbacks;

import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies.RiskEvaluatorServiceProxy;
import org.springframework.stereotype.Service;

@Service
public class RiskEvaluatorFallback implements RiskEvaluatorServiceProxy {
    @Override
    public double getRemoteRiskEvaluation(String id, String movementSens, double movementAmount) {
        return -1_000_000_000;
    }
}