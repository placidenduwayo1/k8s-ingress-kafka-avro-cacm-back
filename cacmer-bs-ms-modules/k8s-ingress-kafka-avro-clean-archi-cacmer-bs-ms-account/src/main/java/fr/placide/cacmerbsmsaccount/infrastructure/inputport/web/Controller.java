package fr.placide.cacmerbsmsaccount.infrastructure.inputport.web;

import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.*;
import fr.placide.cacmerbsmsaccount.domain.inputport.AccountInputService;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/bs-ms-account")
public class Controller {
    @Value("${welcome}")
    private String [] welcome;
    private final AccountInputService inputService;
    public Controller(AccountInputService inputService) {
        this.inputService = inputService;
    }
    @GetMapping(value = "")
    public Map<String, Object> getWelcome(){
        return Map.of(welcome[0], welcome[1]);
    }
    @GetMapping(value = "/accounts/customers/name/{lastname}")
    public List<Account> getAccountsByCustomersName(@PathVariable(name = "lastname") String customerLastname) throws RemoteCustomerApiException {
        return inputService.getAccountsByCustomersName(customerLastname);
    }
    @GetMapping(value = "/accounts/customers/id/{id}")
    public List<Account> getAccountByCustomerId(@PathVariable(name = "id") String id){
        return inputService.getAccountsByCustomer(id);
    }
    @PostMapping(value = "/accounts")
    public Account create(@RequestBody AccountDto dto)throws RemoteCustomerApiStatusInvalidException,
            AccountTypeInvalidException, RemoteCustomerApiException, AccountFieldsInvalidException {
        return inputService.createAccount(dto);
    }
    @GetMapping(value = "/accounts")
    public List<Account> getAll(){
        return inputService.getAllAccounts();
    }
    @GetMapping(value = "/accounts/id/{id}")
    public Account get(@PathVariable(name = "id") String id) throws AccountNotFoundException {
        return inputService.getAccountById(id);
    }
    @DeleteMapping(value = "/accounts/id/{id}")
    public String delete(@PathVariable(name = "id") String id) throws AccountNotFoundException, RemoteCustomerApiException {
        return inputService.deleteAccount(id);
    }
    @PutMapping(value = "/accounts/id/{id}")
    public Account update(@RequestBody AccountDto payload, @PathVariable(name = "id") String id) throws RemoteCustomerApiStatusInvalidException,
            AccountTypeInvalidException, RemoteCustomerApiException, AccountNotFoundException, AccountFieldsInvalidException {
        return inputService.update(payload,id);
    }

}
