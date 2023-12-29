package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies;

import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.fallbacks.AccountFallback;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.AccountModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "k8s-ingress-kafka-avro-cacmer-bs-ms-account", url = "http://k8s-ingress-kafka-avro-cacmer-bs-ms-account:8883",
path = "/bs-ms-account", fallback = AccountFallback.class)
@Qualifier(value = "accountserviceproxy")
public interface AccountServiceProxy {
    @GetMapping(value = "/accounts/id/{id}")
    AccountModel getRemoteAccountById(@PathVariable(name = "id") String id);
    @GetMapping(value = "/accounts/customers/id/{id}")
    List<AccountModel> getRemoteAccountsByCustomerId(@PathVariable(name = "id") String id);
}
