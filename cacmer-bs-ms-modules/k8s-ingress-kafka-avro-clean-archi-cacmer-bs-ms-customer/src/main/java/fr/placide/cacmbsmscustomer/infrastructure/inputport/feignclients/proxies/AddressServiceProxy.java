package fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.proxies;

import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.fallbacks.AddressFallback;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AddressModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "k8s-ingress-kafka-avro-cacmer-bs-ms-address",url = "http://k8s-ingress-kafka-avro-cacmer-bs-ms-address:8881",
path = "/bs-ms-address", fallback = AddressFallback.class)
@Qualifier(value = "addressserviceproxy")
public interface AddressServiceProxy {
    @GetMapping(value = "/addresses/id/{id}")
    AddressModel getRemoteAddressById(@PathVariable(name = "id") String id);
    @GetMapping(value = "/addresses/city/{city}")
    List<AddressModel> getRemoteAddressesByCity(@PathVariable(name = "city") String city);
}
