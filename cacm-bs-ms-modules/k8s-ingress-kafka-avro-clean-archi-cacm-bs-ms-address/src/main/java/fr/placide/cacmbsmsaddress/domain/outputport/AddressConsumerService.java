package fr.placide.cacmbsmsaddress.domain.outputport;

import fr.placide.cacmbsmsaddress.domain.avro.AddressAvro;
import fr.placide.cacmbsmsaddress.domain.beans.Address;

public interface AddressConsumerService {
    Address consumeAddressCreateEvent(AddressAvro avro, String topic);
    Address consumeAddressEditEvent(AddressAvro avro, String topic);
    Address consumeAddressDeleteEvent(AddressAvro avro, String topic);
}
