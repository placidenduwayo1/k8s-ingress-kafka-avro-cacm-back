package fr.placide.cacmerbsmsaccount.infrastructure.outputport.services;

import fr.placide.cacmerbsmsaccount.domain.avro.AccountAvro;
import fr.placide.cacmerbsmsaccount.domain.outputport.AccountProducerService;
import fr.placide.cacmerbsmsaccount.domain.topics.TopicsParams;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountProducerServiceImpl implements AccountProducerService {
    private final KafkaTemplate<String,AccountAvro> kafkaTemplate;
    @Override
    public AccountAvro produceKafkaEventCreateAccount(AccountAvro avro) {
        kafkaTemplate.send(buildKafkaMsg(avro, TopicsParams.TOPICS.get(0)));
        return avro;
    }

    @Override
    public AccountAvro produceKafkaEventUpdateAccount(AccountAvro avro) {
        kafkaTemplate.send(buildKafkaMsg(avro,TopicsParams.TOPICS.get(1)));
        return avro;
    }

    @Override
    public AccountAvro produceKafkaEventDeleteAccount(AccountAvro avro) {
        kafkaTemplate.send(buildKafkaMsg(avro,TopicsParams.TOPICS.get(2)));
        return avro;
    }
    private Message<?> buildKafkaMsg(AccountAvro avro, String topic){
        return MessageBuilder.withPayload(avro)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();
    }
}
