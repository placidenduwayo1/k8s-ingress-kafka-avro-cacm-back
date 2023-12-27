package fr.placide.cacmbsmscustomer.domain.inputport;

import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.*;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerDto;

import java.util.List;

public interface CustomerInputService {
    Customer createCustomer(CustomerDto customerDto) throws CustomerFieldsInvalidException,
            CustomerRiskInvalidException, CustomerStatusInvalidException, CustomerAlreadyExistsException, RemoteAddressApiException;
    List<Customer> getAllCustomers();
    List<Customer> getCustomerByLastname(String lastname) throws CustomerNotFoundException;
    Customer updateCustomer(CustomerDto dto, String customerId) throws CustomerNotFoundException, RemoteAddressApiException,
            CustomerFieldsInvalidException, CustomerRiskInvalidException, CustomerStatusInvalidException;
    String deleteCustomer(String customerId) throws CustomerNotFoundException, RemoteAddressApiException;
    Customer getCustomer(String id) throws CustomerNotFoundException, RemoteAddressApiException;
    List<Customer> getCustomersByAddress(String id) throws RemoteAddressApiException;

    List<Customer> getCustomersByCity(String city) throws RemoteAddressApiException;
}
