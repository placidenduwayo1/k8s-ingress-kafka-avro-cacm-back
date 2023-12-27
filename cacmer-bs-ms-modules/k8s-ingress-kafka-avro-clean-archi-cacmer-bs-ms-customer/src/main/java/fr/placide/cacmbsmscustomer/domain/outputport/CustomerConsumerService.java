package fr.placide.cacmbsmscustomer.domain.outputport;

import fr.placide.cacmbsmscustomer.domain.avro.CustomerAvro;
import fr.placide.cacmbsmscustomer.domain.beans.Customer;

public interface CustomerConsumerService {
    Customer consumeCustomerCreateEvent(CustomerAvro avro, String topic);
    Customer consumeCustomerEditEvent(CustomerAvro avro, String topic);
    Customer consumeCustomerDeleteEvent(CustomerAvro avro, String topic);
}
