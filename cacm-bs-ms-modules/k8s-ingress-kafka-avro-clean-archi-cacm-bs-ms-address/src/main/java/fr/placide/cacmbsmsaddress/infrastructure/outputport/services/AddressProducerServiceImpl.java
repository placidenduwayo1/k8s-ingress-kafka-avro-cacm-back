package fr.placide.cacmbsmsaddress.infrastructure.outputport.services;

import fr.placide.cacmbsmsaddress.domain.avro.AddressAvro;
import fr.placide.cacmbsmsaddress.domain.outputport.AddressProducerService;
import fr.placide.cacmbsmsaddress.domain.topics.TopicsParams;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressProducerServiceImpl implements AddressProducerService {
    private final KafkaTemplate<String, AddressAvro> kafkaTemplate;
    @Override
    public AddressAvro produceKafkaEventCreateAddress(AddressAvro avro) {
        kafkaTemplate.send(buildKafkaMessage(avro, TopicsParams.TOPICS.get(0)));
        return avro;
    }
    @Override
    public AddressAvro produceKafkaEventUpdateAddress(AddressAvro avro) {
        kafkaTemplate.send(buildKafkaMessage(avro,TopicsParams.TOPICS.get(1)));
        return avro;
    }
    @Override
    public AddressAvro produceKafkaEventDeleteAddress(AddressAvro avro)  {
        kafkaTemplate.send(buildKafkaMessage(avro,TopicsParams.TOPICS.get(2)));
        return avro;
    }
    private Message<?> buildKafkaMessage(AddressAvro payload, String topic){
        return MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();
    }
}
