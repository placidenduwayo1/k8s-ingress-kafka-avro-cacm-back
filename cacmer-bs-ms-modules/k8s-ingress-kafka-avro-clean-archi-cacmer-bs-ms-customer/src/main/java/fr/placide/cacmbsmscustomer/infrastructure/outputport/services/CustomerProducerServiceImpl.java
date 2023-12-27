package fr.placide.cacmbsmscustomer.infrastructure.outputport.services;

import fr.placide.cacmbsmscustomer.domain.avro.CustomerAvro;
import fr.placide.cacmbsmscustomer.domain.topics.TopicsParams;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerProducerServiceImpl implements CustomerProducerService {
    private final KafkaTemplate<String, CustomerAvro> kafkaTemplate;
    @Override
    public CustomerAvro produceKafkaEventCreateCustomer(CustomerAvro customerAvro) {
        kafkaTemplate.send(buildKafkaMessage(customerAvro, TopicsParams.TOPICS.get(0)));
        return customerAvro;
    }
    @Override
    public CustomerAvro produceKafkaEventUpdateCustomer(CustomerAvro customerAvro) {
        kafkaTemplate.send(buildKafkaMessage(customerAvro, TopicsParams.TOPICS.get(1)));
        return customerAvro;
    }
    @Override
    public CustomerAvro produceKafkaEventDeleteCustomer(CustomerAvro customerAvro) {
        kafkaTemplate.send(buildKafkaMessage(customerAvro, TopicsParams.TOPICS.get(2)));
        return customerAvro;
    }
    private Message<?> buildKafkaMessage(CustomerAvro customerAvro,String topic){
        return MessageBuilder.withPayload(customerAvro)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();
    }
}
