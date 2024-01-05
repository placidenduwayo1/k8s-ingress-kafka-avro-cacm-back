package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies;

import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.fallbacks.RiskEvaluatorFallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "k8s-ingress-cacmer-risk-evaluator-service", url = "http://k8s-ingress-cacmer-risk-evaluator-service:8880",
path = "/risk-evaluator", fallback = RiskEvaluatorFallback.class)
@Qualifier(value = "riskevaluatorserviceproxy")
public interface RiskEvaluatorServiceProxy {
    @GetMapping(value = "/{id}/{movementSens}/{movementAmount}")
    double getRemoteRiskEvaluation(@PathVariable(name = "id") String id,
                                   @PathVariable(name = "movementSens") String movementSens,
                                   @PathVariable(name = "movementAmount") double movementAmount);
}
