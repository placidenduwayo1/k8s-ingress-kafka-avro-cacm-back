package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies;

import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.fallbacks.RiskEvaluatorFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "k8s-ingress-cacmer-risk-evaluator-service", url = "http://localhost:8880",
path = "/risk-evaluator", fallback = RiskEvaluatorFallback.class)
public interface RiskEvaluatorServiceProxy {
    @GetMapping(value = "/evaluate/{mvtSens}/{mvtAmount}/{customerRisk}/{accountBalance}/{accountOverdraft}")
    double getRemoteRiskEvaluatorToEvaluate(@PathVariable(name = "mvtSens") String mvtSens,
                                             @PathVariable(name = "mvtAmount") double mvtAmount,
                                             @PathVariable(name = "customerRisk") String customerRisk,
                                             @PathVariable(name = "accountBalance") double accountBalance,
                                             @PathVariable(name = "accountOverdraft") double accountOverdraft);
}
