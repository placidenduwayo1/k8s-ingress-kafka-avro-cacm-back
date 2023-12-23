package fr.placide.cacmbsmscustomer.domain.outputport;


import fr.placide.cacmbsmscustomer.domain.beans.Address;

import java.util.List;

public interface RemoteAddressOutputService {
    Address getRemoteAddressById(String addressId);
    List<Address> getRemoteAddressesByCity(String city);
}
