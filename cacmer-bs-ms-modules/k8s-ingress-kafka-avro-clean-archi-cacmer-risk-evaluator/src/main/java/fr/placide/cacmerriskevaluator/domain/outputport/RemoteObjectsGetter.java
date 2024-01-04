package fr.placide.cacmerriskevaluator.domain.outputport;

import fr.placide.cacmerriskevaluator.domain.beans.Account;
import fr.placide.cacmerriskevaluator.domain.beans.Customer;

public interface RemoteObjectsGetter {
    Account getRemoteAccountById(String id);
    Customer getRemoteCustomerById(String id);
}
