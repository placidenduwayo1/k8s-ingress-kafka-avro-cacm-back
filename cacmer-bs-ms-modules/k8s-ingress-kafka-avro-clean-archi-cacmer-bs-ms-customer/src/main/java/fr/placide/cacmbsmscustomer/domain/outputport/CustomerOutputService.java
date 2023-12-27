package fr.placide.cacmbsmscustomer.domain.outputport;

import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.CustomerNotFoundException;

import java.util.List;

public interface CustomerOutputService {
    void createCustomer(Customer customer);
    List<Customer> getAllCustomers();
    List<Customer> getCustomerByLastname(String lastname);
    List<Customer> getCustomerByFirstnameAndLastname(String firstname, String lastname);
    Customer getCustomer(String id) throws CustomerNotFoundException;
    void update(Customer customer);
    void delete(Customer customer);
    List<Customer> getCustomersByAddress(String id);
}
