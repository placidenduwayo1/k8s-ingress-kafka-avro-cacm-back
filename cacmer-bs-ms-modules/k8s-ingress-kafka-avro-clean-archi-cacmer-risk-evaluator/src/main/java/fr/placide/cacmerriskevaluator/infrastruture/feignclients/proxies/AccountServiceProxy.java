package fr.placide.cacmerriskevaluator.infrastruture.feignclients.proxies;

import fr.placide.cacmerriskevaluator.infrastruture.feignclients.fallbacks.AccountFallback;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.AccountModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "k8s-ingress-kafka-avro-cacmer-bs-ms-account", url = "http://localhost:8883",
path = "/bs-ms-account", fallback = AccountFallback.class)
@Qualifier(value = "accountserviceproxy")
public interface AccountServiceProxy {
    @GetMapping(value = "/accounts/id/{id}")
    AccountModel getRemoteAccountById(@PathVariable(name = "id") String id);
}
