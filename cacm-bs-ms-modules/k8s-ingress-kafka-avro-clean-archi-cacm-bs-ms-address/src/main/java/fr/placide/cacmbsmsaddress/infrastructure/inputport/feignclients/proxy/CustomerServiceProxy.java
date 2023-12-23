package fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.proxy;

import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.fallback.CustomerFallback;
import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.model.CustomerModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "k8s-ingress-kafka-avro-cacm-bs-ms-customer", url = "http://k8s-ingress-kafka-avro-cacm-bs-ms-customer:8882",
path = "bs-ms-customer", fallback = CustomerFallback.class)
public interface CustomerServiceProxy {
    @GetMapping(value = "/customers/id/{customerId}")
    CustomerModel getCustomerId(@PathVariable(name = "customerId") String customerId);
}
