package fr.placide.cacmbsmsaddress.domain.outputport;


import fr.placide.cacmbsmsaddress.domain.beans.Address;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.AddressNotFoundException;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;

import java.util.List;

public interface AddressOutputService {
    void create(Address address);
    Address getAddressById(String addressId) throws AddressNotFoundException;
    List<Address> getAddressesByCity(String city);
    List<Address> getAddressByInfo(AddressDto dto);
    List<Address> getAllAddresses();
    void update(Address address);
    void delete(Address  address);
}
