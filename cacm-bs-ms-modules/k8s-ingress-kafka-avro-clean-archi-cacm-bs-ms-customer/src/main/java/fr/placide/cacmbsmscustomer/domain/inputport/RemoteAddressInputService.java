package fr.placide.cacmbsmscustomer.domain.inputport;


import fr.placide.cacmbsmscustomer.domain.beans.Address;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.RemoteAddressApiException;

import java.util.List;

public interface RemoteAddressInputService {
    Address getRemoteAddressById(String addressId) throws RemoteAddressApiException;
    List<Address> getRemoteAddressesByCity(String city) throws RemoteAddressApiException;
}
