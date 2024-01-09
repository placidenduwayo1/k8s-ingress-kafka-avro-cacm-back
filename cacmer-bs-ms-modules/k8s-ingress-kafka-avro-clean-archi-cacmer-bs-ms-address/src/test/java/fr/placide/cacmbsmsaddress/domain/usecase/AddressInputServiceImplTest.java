package fr.placide.cacmbsmsaddress.domain.usecase;

import fr.placide.cacmbsmsaddress.domain.avro.AddressAvro;
import fr.placide.cacmbsmsaddress.domain.beans.Address;
import fr.placide.cacmbsmsaddress.domain.beans.Customer;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.*;
import fr.placide.cacmbsmsaddress.domain.outputport.AddressOutputService;
import fr.placide.cacmbsmsaddress.domain.outputport.AddressProducerService;
import fr.placide.cacmbsmsaddress.domain.outputport.RemoteCustomerService;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

class AddressInputServiceImplTest {
    private static final String ADDRESS_ID = "address-id";
    @Mock
    private AddressProducerService addressProducerService;
    @Mock
    private AddressOutputService addressOutputService;
    @Mock
    private RemoteCustomerService remoteCustomerService;
    @InjectMocks
    private AddressInputServiceImpl underTest;
    private Address address;
    private AddressDto dto;

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
    void createAddress() throws AddressAlreadyExistsException, AddressFieldsInvalidException {
        //PREPARE
        AddressAvro avro = Mapper.toAvro(address);
        //EXECUTE
        Mockito.when(addressProducerService.produceKafkaEventCreateAddress(Mockito.any(AddressAvro.class))).thenReturn(avro);
        Mockito.doNothing().when(addressOutputService).create(address);
        Address address1 = underTest.createAddress(dto);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(addressProducerService, Mockito.atLeast(1)).produceKafkaEventCreateAddress(Mockito.any(AddressAvro.class));
            Assertions.assertNotNull(address1);
        });
    }

    @Test
    void getAddressById() throws AddressNotFoundException {
        //PREPARE
        //EXECUTE
        Mockito.when(addressOutputService.getAddressById(ADDRESS_ID)).thenReturn(address);
        Address address1 = underTest.getAddressById(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(addressOutputService, Mockito.atLeast(1)).getAddressById(ADDRESS_ID);
            Assertions.assertNotNull(address1);
            Assertions.assertEquals(address, address1);
        });
    }

    @Test
    void getAddressesByCity() throws AddressNotFoundException {
        //PREPARE
        Address address1 = address;
        Address address2 = address;
        address1.setAddressId("address-id1");
        address1.setNum(34);
        address2.setAddressId("address-id2");
        address2.setNum(35);
        List<Address> addresses = List.of(address, address1, address2);
        //EXECUTE
        Mockito.when(addressOutputService.getAddressesByCity(address.getCity())).thenReturn(addresses);
        List<Address> addresses1 = underTest.getAddressesByCity(address.getCity());
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(addressOutputService, Mockito.atLeast(1)).getAddressesByCity(address.getCity());
            Assertions.assertEquals(3, addresses1.size());
            Assertions.assertEquals(addresses, addresses1);
        });
    }

    @Test
    void updateAddress() throws AddressNotFoundException, AddressFieldsInvalidException {
        //PREPARE
        AddressAvro avro = Mapper.toAvro(address);
        //EXECUTE
        Mockito.when(addressOutputService.getAddressById(ADDRESS_ID)).thenReturn(address);
        Mockito.when(addressProducerService.produceKafkaEventUpdateAddress(Mockito.any(AddressAvro.class))).thenReturn(avro);
        Mockito.doNothing().when(addressOutputService).update(address);
        Address address1 = underTest.updateAddress(dto,ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("", () -> {
            Mockito.verify(addressProducerService, Mockito.atLeast(1)).produceKafkaEventUpdateAddress(Mockito.any(AddressAvro.class));
            Assertions.assertNotNull(address1);
        });
    }

    @Test
    void deleteAddress() throws AddressNotFoundException, AddressAssignedCustomerException, RemoteCustomerAPIException {
        //PREPARE
        AddressAvro avro = Mapper.toAvro(address);
        List<Customer> customers = Collections.emptyList();
        //EXECUTE
        Mockito.when(remoteCustomerService.getRemoteCustomersByAddress(ADDRESS_ID)).thenReturn(customers);
        Mockito.when(addressOutputService.getAddressById(ADDRESS_ID)).thenReturn(address);
        Mockito.when(addressProducerService.produceKafkaEventDeleteAddress(avro)).thenReturn(avro);
        String msg = underTest.deleteAddress(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(msg);
            Mockito.verify(remoteCustomerService, Mockito.atLeast(1)).getRemoteCustomersByAddress(ADDRESS_ID);
            Mockito.verify(addressOutputService, Mockito.atLeast(1)).getAddressById(ADDRESS_ID);
            Mockito.verify(addressProducerService, Mockito.atLeast(1)).produceKafkaEventDeleteAddress(avro);
        });

    }

    @Test
    void getAllAddresses() {
        //PREPARE
        List<Address> addresses = List.of(address);
        //EXECUTE
        Mockito.when(addressOutputService.getAllAddresses()).thenReturn(addresses);
        List<Address> addresses1 = underTest.getAllAddresses();
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(addressOutputService, Mockito.atLeast(1)).getAllAddresses();
            Assertions.assertFalse(addresses1.isEmpty());
        });
    }
}