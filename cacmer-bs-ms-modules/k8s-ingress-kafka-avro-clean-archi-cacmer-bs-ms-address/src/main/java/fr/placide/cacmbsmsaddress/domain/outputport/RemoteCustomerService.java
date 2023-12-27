package fr.placide.cacmbsmsaddress.domain.outputport;

import fr.placide.cacmbsmsaddress.domain.beans.Customer;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.RemoteCustomerAPIException;

import java.util.List;

public interface RemoteCustomerService {
   List<Customer> getRemoteCustomersByAddress(String addressId) throws RemoteCustomerAPIException;
}
