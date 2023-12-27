package fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.proxy;

import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.fallback.CustomerFallback;
import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.model.CustomerModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "k8s-ingress-kafka-avro-cacmer-bs-ms-customer", url = "http://k8s-ingress-kafka-avro-cacmer-bs-ms-customer:8882",
path = "bs-ms-customer", fallback = CustomerFallback.class)
@Qualifier(value = "customerserviceproxy")
public interface CustomerServiceProxy {
    @GetMapping(value = "/customers/addresses/id/{id}")
    List<CustomerModel> getRemoteCustomersByAddress(@PathVariable(name = "id") String addressId);
}
