package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies;

import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.fallbacks.RiskEvaluatorFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "k8s-ingress-cacmer-risk-evaluator-service", url = "http://localhost:8880",
path = "/risk-evaluator", fallback = RiskEvaluatorFallback.class)
public interface RiskEvaluatorServiceProxy {
    @GetMapping(value = "/{id}/{movementSens}/{movementAmount}")
    double getRemoteRiskEvaluatorToEvaluate(@PathVariable(name = "id") String id,
                                             @PathVariable(name = "movementSens") String movementSens,
                                             @PathVariable(name = "movementAmount") double movementAmount);
}
