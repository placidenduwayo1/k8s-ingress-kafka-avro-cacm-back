package fr.placide.cacmerbsmsaccount.domain.inputport;

import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.RemoteCustomerApiException;

import java.util.List;

public interface RemoteCustomerInputService {
    Customer getRemoteCustomerById(String id) throws RemoteCustomerApiException;
    List<Customer> getRemoteCustomersByName(String lastname) throws RemoteCustomerApiException;
}
