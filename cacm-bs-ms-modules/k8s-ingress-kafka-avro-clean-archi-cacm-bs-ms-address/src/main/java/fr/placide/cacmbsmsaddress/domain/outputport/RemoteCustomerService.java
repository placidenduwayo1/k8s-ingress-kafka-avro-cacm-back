package fr.placide.cacmbsmsaddress.domain.outputport;

import fr.placide.cacmbsmsaddress.domain.beans.Customer;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.RemoteCustomerAPIException;

public interface RemoteCustomerService {
   Customer getRemoteCustomer(String customerId) throws RemoteCustomerAPIException;
}
