package fr.placide.cacmbsmscustomer.infrastructure.outputport.services;

import fr.placide.cacmbsmscustomer.domain.avro.Address;
import fr.placide.cacmbsmscustomer.domain.avro.CustomerAvro;
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

class CustomerProducerServiceImplTest {
    @Mock
    private KafkaTemplate<String, CustomerAvro> kafkaTemplate;
    @InjectMocks
    private CustomerProducerServiceImpl underTest;
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse(
            "confluentinc/cp-kafka"));
    @BeforeEach
    void setUp() {
        KAFKA_CONTAINER.start();
        String bootstraps = KAFKA_CONTAINER.getBootstrapServers();
        System.setProperty("bootstraps",bootstraps);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void produceKafkaEventCustomer() {
        Address address = Address.newBuilder()
                .setAddressId("id")
                .setNum(33)
                .setStreet("Allée Clémentine Deman")
                .setCity("Lille")
                .setCountry("France")
                .setPb(5900)
                .build();
        CustomerAvro avro = CustomerAvro.newBuilder()
                .setCustomerId("id")
                .setAddress(address)
                .setAddressId(address.getAddressId())
                .setFirstname("test")
                .setLastname("test")
                .setCreatedAt("now")
                .setRisk("low")
                .setStatus("active")
                .build();
        Message<CustomerAvro> message0 = MessageBuilder.withPayload(avro).setHeader(KafkaHeaders.TOPIC,"create").build();
        Message<CustomerAvro> message1 = MessageBuilder.withPayload(avro).setHeader(KafkaHeaders.TOPIC,"update").build();
        Message<CustomerAvro> message2 = MessageBuilder.withPayload(avro).setHeader(KafkaHeaders.TOPIC,"delete").build();
        kafkaTemplate.send(message0);
        kafkaTemplate.send(message1);
        kafkaTemplate.send(message2);
        CustomerAvro avro0 = underTest.produceKafkaEventCreateCustomer(avro);
        CustomerAvro avro1 = underTest.produceKafkaEventUpdateCustomer(avro);
        CustomerAvro avro2 = underTest.produceKafkaEventDeleteCustomer(avro);
        Assertions.assertAll("",()->{
            Mockito.verify(kafkaTemplate,Mockito.times(1)).send(message0);
            Mockito.verify(kafkaTemplate,Mockito.times(1)).send(message1);
            Mockito.verify(kafkaTemplate,Mockito.times(1)).send(message2);
            Assertions.assertNotNull(avro0);
            Assertions.assertNotNull(avro1);
            Assertions.assertNotNull(avro2);
        });
    }
}