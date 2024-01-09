package fr.placide.cacmbsmsaddress.infrastructure.inputport.web;

import fr.placide.cacmbsmsaddress.domain.beans.Address;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.*;
import fr.placide.cacmbsmsaddress.domain.inputport.AddressInputService;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;


class AddressControllerTest {
    @Mock
    private AddressInputService addressInputService;
    @InjectMocks
    private AddressController underTest;
    private Address address;
    private AddressDto dto;
    private static final String ADDRESS_ID = "address-id";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dto = AddressDto.builder()
                .street("Allée Clémentine Deman")
                .num(33)
                .pb(5900)
                .city("Lille")
                .country("France")
                .build();
        address = Mapper.map(dto);
        address.setAddressId(ADDRESS_ID);
    }

    @Test
    void gets() throws AddressNotFoundException {
        //EXECUTE
        Mockito.when(addressInputService.getAddressById(ADDRESS_ID)).thenReturn(address);
        Address address1 = underTest.get(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(addressInputService, Mockito.atLeast(1)).getAddressById(ADDRESS_ID);
            Assertions.assertNotNull(address1);
        });
    }

    @Test
    void create() throws AddressAlreadyExistsException, AddressFieldsInvalidException {
        //EXECUTE
        Mockito.when(addressInputService.createAddress(dto)).thenReturn(address);
        Address address1 = underTest.create(dto);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(address1);
            Mockito.verify(addressInputService, Mockito.times(1)).createAddress(dto);
        });
    }

    @Test
    void get() throws AddressNotFoundException {
        //EXECUTE
        Mockito.when(addressInputService.getAddressById(ADDRESS_ID)).thenReturn(address);
        Address address1 = underTest.get(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(address1);
            Mockito.verify(addressInputService, Mockito.times(1)).getAddressById(ADDRESS_ID);
        });
    }

    @Test
    void testGets() throws AddressNotFoundException {
        //PREPARE
        List<Address>  addresses = List.of(address);
        String city ="city";
        //EXECUTE
        Mockito.when(addressInputService.getAddressesByCity(city)).thenReturn(addresses);
        List<Address>  addresses1 = underTest.gets(city);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(addresses1);
            Mockito.verify(addressInputService, Mockito.times(1)).getAddressesByCity(city);
        });
    }

    @Test
    void update() throws AddressNotFoundException, AddressFieldsInvalidException {
        //PREPARE
        Address newAddress = address;
        newAddress.setNum(34);
        //EXECUTE
        Mockito.when(addressInputService.updateAddress(dto, ADDRESS_ID)).thenReturn(newAddress);
        Address address1 = underTest.update(dto,ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(address1);
            Mockito.verify(addressInputService, Mockito.times(1)).updateAddress(dto,ADDRESS_ID);
        });
    }

    @Test
    void delete() throws AddressNotFoundException, AddressAssignedCustomerException, RemoteCustomerAPIException {
        //EXECUTE
        Mockito.when(addressInputService.getAddressById(ADDRESS_ID)).thenReturn(address);
        Mockito.when(addressInputService.deleteAddress(ADDRESS_ID)).thenReturn("deleted");
        String s = underTest.delete(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(s);
            Mockito.verify(addressInputService, Mockito.times(1)).deleteAddress(ADDRESS_ID);
        });
    }
}