package fr.placide.cacmbsmsaddress.infrastructure.outputport.services;

import fr.placide.cacmbsmsaddress.domain.avro.AddressAvro;
import fr.placide.cacmbsmsaddress.domain.beans.Address;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

class AddressProducerServiceImplTest {
    private static final String ADDRESS_ID = "address-id";
    @Mock
    private KafkaTemplate<String, AddressAvro> kafkaTemplate;
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse(
            "confluentinc/cp-kafka"));
    private Address address;
    private AddressDto dto;
    @InjectMocks
    private AddressProducerServiceImpl underTest;
    private static final String[] TOPICS ={"create","update","delete"};

    @BeforeEach
    void setUp() {
        KAFKA_CONTAINER.start();
        String brokers = KAFKA_CONTAINER.getBootstrapServers();
        System.setProperty("kafka-brokers", brokers);

        dto = AddressDto.builder()
                .street("Allée Clémentine Deman")
                .num(33)
                .pb(5900)
                .city("Lille")
                .country("France")
                .build();
        address = Mapper.map(dto);
        address.setAddressId(ADDRESS_ID);

        MockitoAnnotations.openMocks(this);
    }

    private Message<?> buildMsg(AddressAvro payload, String topic){
        return MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();
    }
    @Test

    void produceKafkaEventAddress() {
        //PREPARE
        AddressAvro avro = Mapper.toAvro(address);
        Message<?> message0 = buildMsg(avro, TOPICS[0]);
        Message<?> message1 = buildMsg(avro, TOPICS[1]);
        Message<?> message2 = buildMsg(avro, TOPICS[2]);
        //EXECUTE
        kafkaTemplate.send(message0);
        AddressAvro avro1 = underTest.produceKafkaEventCreateAddress(avro);
        kafkaTemplate.send(message1);
        AddressAvro avro2 = underTest.produceKafkaEventUpdateAddress(avro);
        kafkaTemplate.send(message2);
        AddressAvro avro3 = underTest.produceKafkaEventDeleteAddress(avro);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(kafkaTemplate, Mockito.atLeast(1)).send(message0);
            Mockito.verify(kafkaTemplate, Mockito.atLeast(1)).send(message1);
            Mockito.verify(kafkaTemplate, Mockito.atLeast(1)).send(message2);
            Assertions.assertEquals(avro1,avro2);
            Assertions.assertEquals(avro1,avro3);
        });
    }
}