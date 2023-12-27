package fr.placide.cacmbsmsaddress.infrastructure.outputport.services;

import fr.placide.cacmbsmsaddress.domain.avro.AddressAvro;
import fr.placide.cacmbsmsaddress.domain.beans.Address;
import fr.placide.cacmbsmsaddress.domain.beans.Customer;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.AddressNotFoundException;
import fr.placide.cacmbsmsaddress.domain.outputport.AddressConsumerService;
import fr.placide.cacmbsmsaddress.domain.outputport.AddressOutputService;
import fr.placide.cacmbsmsaddress.domain.outputport.RemoteCustomerService;
import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.proxy.CustomerServiceProxy;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressModel;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.repository.AddressRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressOutputServiceImpl implements AddressOutputService, AddressConsumerService, RemoteCustomerService {
    private final AddressRepo addressRepo;
    private final CustomerServiceProxy customerServiceProxy;

    public AddressOutputServiceImpl(AddressRepo addressRepo, @Qualifier(value = "customerserviceproxy") CustomerServiceProxy customerServiceProxy) {
        this.addressRepo = addressRepo;
        this.customerServiceProxy = customerServiceProxy;
    }

    @Override
    @KafkaListener(topics = "address-created", groupId = "address")
    public Address consumeAddressCreateEvent(@Payload(required = false) AddressAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Address address = map(avro);
        log.info("address to create:<{}> consumed from topic:<{}>", address, topic);
        return address;
    }

    @Override
    public void create(Address address) {
        AddressAvro avro = Mapper.toAvro(address);
        Address consumed = consumeAddressCreateEvent(avro, "address-created");
        addressRepo.save(Mapper.map(consumed));
    }

    @Override
    public Address getAddressById(String addressId) throws AddressNotFoundException {
        AddressModel model = addressRepo.findById(addressId).orElseThrow(AddressNotFoundException::new);
        return map(model);
    }

    @Override
    public List<Address> getAddressesByCity(String city) {
        return map(addressRepo.findByCity(city));
    }

    @Override
    @KafkaListener(topics = "address-edited", groupId = "address")
    public Address consumeAddressEditEvent(@Payload AddressAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Address address = map(avro);
        log.info("address to update:<{}> consumed from topic:<{}>", address, topic);
        return address;
    }

    @Override
    public void update(Address address) {
        AddressAvro avro = Mapper.toAvro(address);
        Address consumed = consumeAddressEditEvent(avro, "address-edited");
        addressRepo.save(Mapper.map(consumed));
    }

    @Override
    @KafkaListener(topics = "address-deleted", groupId = "address")
    public Address consumeAddressDeleteEvent(@Payload(required = false) AddressAvro avro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Address address = map(avro);
        log.info("address to delete:<{}> consumed from topic:<{}>", address, topic);
        return address;
    }

    @Override
    public void delete(Address address) {
        AddressAvro avro = Mapper.toAvro(address);
        Address consumed = consumeAddressEditEvent(avro, "address-deleted");
        addressRepo.deleteById(consumed.getAddressId());
    }

    @Override
    public List<Address> getAddressByInfo(AddressDto dto) {
        return map(addressRepo.findByNumAndStreetAndPbAndCityAndCountry(
                dto.getNum(), dto.getStreet(), dto.getPb(),
                dto.getCity(), dto.getCountry()));
    }

    @Override
    public List<Address> getAllAddresses() {
        return map(addressRepo.findAll());
    }

    private List<Address> map(List<AddressModel> list) {
        return list.stream().map(Mapper::map).toList();
    }

    private Address map(AddressModel model) {
        return Mapper.map(model);
    }

    private Address map(AddressAvro avro) {
        return Mapper.map(avro);
    }

    @Override
    public List<Customer> getRemoteCustomersByAddress(String addressId) {
        return customerServiceProxy.getRemoteCustomersByAddress(addressId).stream()
                .map(Mapper::map)
                .toList();
    }
}
