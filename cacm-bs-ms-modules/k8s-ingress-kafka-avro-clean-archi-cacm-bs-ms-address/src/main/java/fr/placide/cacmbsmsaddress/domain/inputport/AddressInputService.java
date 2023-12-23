package fr.placide.cacmbsmsaddress.domain.inputport;

import fr.placide.cacmbsmsaddress.domain.beans.Address;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.*;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;

import java.util.List;

public interface AddressInputService {
    Address createAddress(AddressDto addressDto) throws AddressAlreadyExistsException, AddressFieldsInvalidException;
    Address getAddressById(String addressId) throws AddressNotFoundException;
    List<Address> getAddressesByCity(String city) throws AddressNotFoundException;
    Address updateAddress(AddressDto payload, String addressId) throws AddressNotFoundException, AddressFieldsInvalidException;
    String deleteAddress(String addressId) throws AddressNotFoundException, RemoteCustomerAPIException, AddressAssignedCustomerException;
    List<Address> getAllAddresses();
}
