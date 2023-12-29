package fr.placide.cacmerbsmsmovement.domain.outputport;

import fr.placide.cacmerbsmsmovement.domain.beans.Customer;
import fr.placide.cacmerbsmsmovement.domain.exceptions.business_exc.RemoteCustomerApiUnreachable;

import java.util.List;

public interface RemoteCustomerService {
    Customer getRemoteCustomerById(String id) throws RemoteCustomerApiUnreachable;
    List<Customer> getRemoteCustomersByName(String lastname);
}
