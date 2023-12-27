package fr.placide.cacmbsmscustomer.infrastructure.inputport.web;

import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.*;
import fr.placide.cacmbsmscustomer.domain.inputport.CustomerInputService;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/bs-ms-customer")
@RequiredArgsConstructor
public class Controller {
    private final CustomerInputService inputService;
    @Value("${welcome}")
    private String[] msg;
    @GetMapping(value = "")
    public Map<String, Object> getWelcome() {
        return Map.of(msg[0],msg[1]);
    }
    @GetMapping(value = "/customers")
    public List<Customer> gets(){
        return inputService.getAllCustomers();
    }
    @PostMapping(value = "/customers")
    public Customer create(@RequestBody CustomerDto dto) throws CustomerFieldsInvalidException,
            CustomerRiskInvalidException, CustomerStatusInvalidException, CustomerAlreadyExistsException, RemoteAddressApiException {
        return inputService.createCustomer(dto);
    }
    @GetMapping(value = "/customers/name/{lastname}")
    public List<Customer> gets(@PathVariable(name = "lastname") String lastname) throws CustomerNotFoundException {
        return inputService.getCustomerByLastname(lastname);
    }

    @GetMapping(value = "/customers/id/{id}")
    public Customer get(@PathVariable(name = "id") String id) throws RemoteAddressApiException,CustomerNotFoundException {
        return inputService.getCustomer(id);
    }
    @PutMapping(value = "/customers/id/{id}")
    public Customer update(@RequestBody CustomerDto dto, @PathVariable(name = "id")  String id) throws RemoteAddressApiException,
            CustomerFieldsInvalidException, CustomerRiskInvalidException, CustomerStatusInvalidException, CustomerNotFoundException {

        return inputService.updateCustomer(dto, id);
    }
    @DeleteMapping(value = "/customers/id/{id}")
    public String delete(@PathVariable(name = "id") String id) throws RemoteAddressApiException, CustomerNotFoundException {
        return inputService.deleteCustomer(id);
    }
    @GetMapping(value = "/customers/addresses/id/{id}")
    public List<Customer> getCustomersByAddress(@PathVariable(name = "id") String id) throws RemoteAddressApiException {
        return inputService.getCustomersByAddress(id);
    }
    @GetMapping(value = "/customers/addresses/city/{city}")
    public List<Customer> getCustomersByCity(@PathVariable(name = "city") String city) throws RemoteAddressApiException {
        return inputService.getCustomersByCity(city);
    }
}
