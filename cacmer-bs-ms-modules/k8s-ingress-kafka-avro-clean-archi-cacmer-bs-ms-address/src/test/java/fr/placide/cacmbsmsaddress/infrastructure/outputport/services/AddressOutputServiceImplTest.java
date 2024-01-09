package fr.placide.cacmbsmsaddress.infrastructure.outputport.services;

import fr.placide.cacmbsmsaddress.domain.avro.AddressAvro;
import fr.placide.cacmbsmsaddress.domain.beans.Address;
import fr.placide.cacmbsmsaddress.domain.beans.Customer;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.AddressNotFoundException;
import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.model.CustomerModel;
import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.proxy.CustomerServiceProxy;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressModel;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.repository.AddressRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class AddressOutputServiceImplTest {
    @Mock
    private AddressRepo addressRepo;
    @Mock
    private CustomerServiceProxy customerServiceProxy;
    @InjectMocks
    private AddressOutputServiceImpl underTest;
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
    void consumeAddressEvent() {
        AddressAvro avro = Mapper.toAvro(address);
        Address address1 = underTest.consumeAddressCreateEvent(avro,"create");
        Address address2 = underTest.consumeAddressEditEvent(avro,"update");
        Address address3 = underTest.consumeAddressDeleteEvent(avro,"delete");
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(address1);
            Assertions.assertNotNull(address2);
            Assertions.assertNotNull(address3);
        });
    }

    @Test
    void create() {
        //PREPARE
        AddressModel model = Mapper.map(address);
        //EXECUTE
        Mockito.when(addressRepo.save(Mockito.any(AddressModel.class))).thenReturn(model);
        underTest.create(address);
        //VERIFY
        Assertions.assertAll("",()-> Mockito.verify(addressRepo, Mockito.times(1)).save(Mockito.any()));
    }

    @Test
    void getAddressById() throws AddressNotFoundException {
        //PREPARE
        Optional<AddressModel> model = Optional.of(Mapper.map(address));
        //EXECUTE
        Mockito.when(addressRepo.findById(ADDRESS_ID)).thenReturn(model);
        Address address1 = underTest.getAddressById(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(addressRepo, Mockito.times(1)).findById(ADDRESS_ID);
            Assertions.assertNotNull(address1);
        });
    }

    @Test
    void getAddressesByCity() {
        //PREPARE
        List<AddressModel> addresses = Stream.of(address).map(Mapper::map).toList();
        //EXECUTE
        Mockito.when(addressRepo.findByCity(address.getCity())).thenReturn(addresses);
        List<Address> addresses1 = underTest.getAddressesByCity(address.getCity());
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(addressRepo, Mockito.times(1)).findByCity(address.getCity());
            Assertions.assertNotNull(addresses1);
        });
    }

    @Test
    void update() {
        //PREPARE
        AddressModel model = Mapper.map(address);
        //EXECUTE
        Mockito.when(addressRepo.save(Mockito.any(AddressModel.class))).thenReturn(model);
        underTest.update(address);
        Assertions.assertAll("",()-> Mockito.verify(addressRepo, Mockito.times(1)).save(Mockito.any()));
    }

    @Test
    void delete() {
        //EXECUTE
        Mockito.doNothing().when(addressRepo).deleteById(ADDRESS_ID);
        underTest.delete(address);
        //VERIFY
        Assertions.assertAll("",()-> Mockito.verify(addressRepo, Mockito.times(1)).deleteById(ADDRESS_ID));
    }

    @Test
    void getAddressByInfo() {
        //PREPARE
        List<AddressModel> addresses = Stream.of(address).map(Mapper::map).toList();
        //EXECUTE
        Mockito.when(addressRepo.findByNumAndStreetAndPbAndCityAndCountry(
                address.getNum(), address.getStreet(), address.getPb(), address.getCity(),address.getCountry()
        )).thenReturn(addresses);
        List<Address> addresses1 = underTest.getAddressByInfo(dto);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(addresses1);
            Assertions.assertEquals(1,addresses1.size());
            Mockito.verify(addressRepo, Mockito.times(1)).findByNumAndStreetAndPbAndCityAndCountry(
                    address.getNum(), address.getStreet(), address.getPb(), address.getCity(),address.getCountry());
        });
    }

    @Test
    void getAllAddresses() {
        //PREPARE
        List<AddressModel> addresses = Stream.of(address).map(Mapper::map).toList();
        //EXECUTE
        Mockito.when(addressRepo.findAll()).thenReturn(addresses);
        List<Address> addresses1 = underTest.getAllAddresses();
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(addresses1);
            Assertions.assertEquals(1,addresses1.size());
            Mockito.verify(addressRepo, Mockito.times(1)).findAll();
        });
    }

    @Test
    void getRemoteCustomersByAddress() {
        //PREPARE
        List<CustomerModel> models = List.of(CustomerModel.builder().build());
        //EXECUTE
        Mockito.when(customerServiceProxy.getRemoteCustomersByAddress(ADDRESS_ID)).thenReturn(models);
        List<Customer> customers = underTest.getRemoteCustomersByAddress(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(customers);
            Mockito.verify(customerServiceProxy, Mockito.times(1)).getRemoteCustomersByAddress(ADDRESS_ID);
        });
    }
}