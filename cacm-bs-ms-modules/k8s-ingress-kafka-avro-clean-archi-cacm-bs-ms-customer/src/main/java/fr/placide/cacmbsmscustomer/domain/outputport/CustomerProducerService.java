package fr.placide.cacmbsmscustomer.domain.outputport;

import fr.placide.cacmbsmscustomer.domain.avro.CustomerAvro;

public interface CustomerProducerService {
    CustomerAvro produceKafkaEventCreateCustomer(CustomerAvro customerAvro);
    CustomerAvro produceKafkaEventUpdateCustomer(CustomerAvro customerAvro);
    CustomerAvro produceKafkaEventDeleteCustomer(CustomerAvro customerAvro);

}
