package fr.placide.cacmbsmsaddress.domain.outputport;

import fr.placide.cacmbsmsaddress.domain.avro.AddressAvro;

public interface AddressProducerService {
    AddressAvro produceKafkaEventCreateAddress(AddressAvro avro);
    AddressAvro produceKafkaEventUpdateAddress(AddressAvro avro);
    AddressAvro produceKafkaEventDeleteAddress(AddressAvro avro);

}
