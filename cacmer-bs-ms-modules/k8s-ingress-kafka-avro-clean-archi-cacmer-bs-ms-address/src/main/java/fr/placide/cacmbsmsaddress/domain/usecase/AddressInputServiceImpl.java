package fr.placide.cacmbsmsaddress.domain.usecase;

import fr.placide.cacmbsmsaddress.domain.avro.AddressAvro;
import fr.placide.cacmbsmsaddress.domain.beans.Address;
import fr.placide.cacmbsmsaddress.domain.beans.Customer;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.*;
import fr.placide.cacmbsmsaddress.domain.inputport.AddressInputService;
import fr.placide.cacmbsmsaddress.domain.outputport.AddressOutputService;
import fr.placide.cacmbsmsaddress.domain.outputport.AddressProducerService;
import fr.placide.cacmbsmsaddress.domain.outputport.RemoteCustomerService;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;

import java.util.List;
import java.util.UUID;

public class AddressInputServiceImpl implements AddressInputService {
    private final AddressProducerService addressProducerService;
    private final AddressOutputService addressOutputService;
    private final RemoteCustomerService remoteCustomerService;

    public AddressInputServiceImpl(AddressProducerService addressProducerService, AddressOutputService addressOutputService,
                                   RemoteCustomerService remoteCustomerService) {
        this.addressProducerService = addressProducerService;
        this.addressOutputService = addressOutputService;
        this.remoteCustomerService = remoteCustomerService;
    }

    private void validateAddress(AddressDto dto) throws AddressFieldsInvalidException {
        if (!AddressValidationTools.isValid(dto)) {
            throw new AddressFieldsInvalidException();
        }
    }

    private void validateAddressExists(AddressDto dto) throws AddressAlreadyExistsException {
        List<Address> addresses = addressOutputService.getAddressByInfo(dto);
        if (!addresses.isEmpty()) {
            throw new AddressAlreadyExistsException();
        }
    }

    @Override
    public Address createAddress(AddressDto addressDto) throws AddressAlreadyExistsException, AddressFieldsInvalidException {
        AddressValidationTools.format(addressDto);
        validateAddress(addressDto);
        validateAddressExists(addressDto);
        Address bean = Mapper.map(addressDto);
        bean.setAddressId(UUID.randomUUID().toString());
        AddressAvro avro = Mapper.toAvro(bean);
        AddressAvro produced = addressProducerService.produceKafkaEventCreateAddress(avro);
        addressOutputService.create(Mapper.map(produced));
        return Mapper.map(produced);
    }

    @Override
    public Address getAddressById(String addressId) throws AddressNotFoundException {
        return addressOutputService.getAddressById(addressId);
    }

    @Override
    public List<Address> getAddressesByCity(String city) throws AddressNotFoundException {
        List<Address> addresses = addressOutputService.getAddressesByCity(city);
        if (addresses.isEmpty()) {
            throw new AddressNotFoundException();
        }
        return addresses;
    }

    @Override
    public Address updateAddress(AddressDto payload, String addressId) throws AddressNotFoundException,
            AddressFieldsInvalidException {
        AddressValidationTools.format(payload);
        validateAddress(payload);
        Address address = getAddressById(addressId);
        Address toUpdate = new Address.AddressBuilder()
                .addressId(address.getAddressId())
                .num(payload.getNum())
                .street(payload.getStreet())
                .pb(payload.getPb())
                .city(payload.getCity())
                .country(payload.getCountry())
                .build();
        AddressAvro avro = addressProducerService.produceKafkaEventUpdateAddress(Mapper.toAvro(toUpdate));
        addressOutputService.update(Mapper.map(avro));
        return Mapper.map(avro);
    }

    @Override
    public String deleteAddress(String addressId) throws AddressNotFoundException, RemoteCustomerAPIException,
            AddressAssignedCustomerException {
        Address address = getAddressById(addressId);
        List<Customer> customers = remoteCustomerService.getRemoteCustomersByAddress(addressId);
        if(!customers.isEmpty()){
            throw new AddressAssignedCustomerException();
        }
        AddressAvro avro = Mapper.toAvro(address);
        AddressAvro produced = addressProducerService.produceKafkaEventDeleteAddress(avro);
        addressOutputService.delete(Mapper.map(produced));
        return "address "+produced+" deleted";
    }
    @Override
    public List<Address> getAllAddresses() {
        return addressOutputService.getAllAddresses();
    }
}
