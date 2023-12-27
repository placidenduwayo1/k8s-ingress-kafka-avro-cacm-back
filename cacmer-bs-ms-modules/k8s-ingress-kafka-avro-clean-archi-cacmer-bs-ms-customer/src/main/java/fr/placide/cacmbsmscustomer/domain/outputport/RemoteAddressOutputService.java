package fr.placide.cacmbsmscustomer.domain.outputport;


import fr.placide.cacmbsmscustomer.domain.beans.Address;
import fr.placide.cacmbsmscustomer.domain.exceptions.business_exc.RemoteAddressApiException;

import java.util.List;

public interface RemoteAddressOutputService {
    Address getRemoteAddressById(String addressId) throws RemoteAddressApiException;
    List<Address> getRemoteAddressesByCity(String city) throws RemoteAddressApiException;
}
