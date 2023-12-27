package fr.placide.cacmerbsmsaccount.domain.outputport;


import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
import fr.placide.cacmerbsmsaccount.domain.exceptions.business_exc.RemoteCustomerApiException;

import java.util.List;

public interface RemoteCustomerOutputService {
    Customer getRemoteCustomerById(String id) throws RemoteCustomerApiException;
    List<Customer> getCustomersByName(String lastname);
}
