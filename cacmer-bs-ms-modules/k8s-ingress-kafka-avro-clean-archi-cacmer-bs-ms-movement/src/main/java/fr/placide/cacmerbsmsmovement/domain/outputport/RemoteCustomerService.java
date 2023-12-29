package fr.placide.cacmerbsmsmovement.domain.outputport;

import fr.placide.cacmerbsmsmovement.domain.beans.Customer;
import fr.placide.cacmerbsmsmovement.domain.exceptions.business_exc.RemoteCustomerApiUnreachable;

public interface RemoteCustomerService {
    Customer getRemoteCustomerById(String id) throws RemoteCustomerApiUnreachable;
}
