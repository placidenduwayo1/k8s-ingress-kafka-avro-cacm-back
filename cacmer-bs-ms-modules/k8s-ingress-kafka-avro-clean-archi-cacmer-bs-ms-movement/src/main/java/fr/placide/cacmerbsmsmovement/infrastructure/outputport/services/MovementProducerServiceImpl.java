package fr.placide.cacmerbsmsmovement.infrastructure.outputport.services;

import fr.placide.cacmerbsmsmovement.domain.avro.MovementAvro;
import fr.placide.cacmerbsmsmovement.domain.outputport.MovementProducerService;
import fr.placide.cacmerbsmsmovement.domain.topics.TopicsParams;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovementProducerServiceImpl implements MovementProducerService {
    private final KafkaTemplate<String, MovementAvro> kafkaTemplate;
    @Override
    public MovementAvro produceKafkaEventCreateMovement(MovementAvro avro) {
        kafkaTemplate.send(buildKafkaMsg(avro, TopicsParams.TOPICS.get(0)));
        return avro;
    }

    @Override
    public MovementAvro produceKafkaEventUpdateMovement(MovementAvro avro) {
        kafkaTemplate.send(buildKafkaMsg(avro, TopicsParams.TOPICS.get(1)));
        return avro;
    }

    @Override
    public MovementAvro produceKafkaEventDeleteMovement(MovementAvro avro) {
        kafkaTemplate.send(buildKafkaMsg(avro, TopicsParams.TOPICS.get(2)));
        return avro;
    }
    private Message<?> buildKafkaMsg(MovementAvro payload, String topic){
        return MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();
    }
}
