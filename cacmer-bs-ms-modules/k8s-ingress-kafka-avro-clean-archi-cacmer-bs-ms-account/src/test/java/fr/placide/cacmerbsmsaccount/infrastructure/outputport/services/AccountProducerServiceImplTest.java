package fr.placide.cacmerbsmsaccount.infrastructure.outputport.services;

import fr.placide.cacmerbsmsaccount.domain.avro.AccountAvro;
import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.mapper.Mapper;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountDto;
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

import java.sql.Timestamp;
import java.time.Instant;

class AccountProducerServiceImplTest {
    private static final KafkaContainer KAFKA_CONTAINER =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka"));
    @Mock
    private KafkaTemplate<String, AccountAvro> kafkaTemplate;
    @InjectMocks
    private AccountProducerServiceImpl underTest;
    private Account account;
    private static final String ACCOUNT_ID = "account-id";
    private static final String CUSTOMER_ID = "customer-id";
    private static final String [] TOPICS = {"created","edited","deleted"};
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        KAFKA_CONTAINER.start();
        String brokers = KAFKA_CONTAINER.getBootstrapServers();
        System.setProperty("kafka brokers", brokers);

        MockitoAnnotations.openMocks(this);

        Customer customer = Customer.builder()
                .customerId(CUSTOMER_ID)
                .firstname("test")
                .lastname("test")
                .createdAt(Timestamp.from(Instant.from(Instant.now())).toString())
                .risk("low")
                .status("active")
                .build();
        AccountDto dto = AccountDto.builder()
                .type("current")
                .balance(50)
                .overdraft(0)
                .customerId(CUSTOMER_ID)
                .build();
        account = Mapper.map(dto);
        account.setCustomer(customer);
        account.setAccountId(ACCOUNT_ID);
    }
    private Message<?> buildMsg(AccountAvro payload, String topic){
        return MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();
    }

    @Test
    void produceKafkaEventAccount() {
        //PREPARE
        AccountAvro avro = Mapper.toAvro(account);
        Message<?> kafkaMsg1 = buildMsg(avro,TOPICS[0]);
        Message<?> kafkaMsg2 = buildMsg(avro,TOPICS[1]);
        Message<?> kafkaMsg3 = buildMsg(avro,TOPICS[2]);
        //EXECUTE
        kafkaTemplate.send(kafkaMsg1);
        kafkaTemplate.send(kafkaMsg2);
        kafkaTemplate.send(kafkaMsg3);
        AccountAvro expected1 = underTest.produceKafkaEventCreateAccount(avro);
        AccountAvro expected2 = underTest.produceKafkaEventUpdateAccount(avro);
        AccountAvro expected3 = underTest.produceKafkaEventDeleteAccount(avro);
        //VERIFY
        Assertions.assertAll("",()->{
            Mockito.verify(kafkaTemplate).send(kafkaMsg1);
            Mockito.verify(kafkaTemplate).send(kafkaMsg2);
            Mockito.verify(kafkaTemplate).send(kafkaMsg3);
            Assertions.assertEquals(expected1,avro);
            Assertions.assertEquals(expected2,avro);
            Assertions.assertEquals(expected3,avro);
        });
    }
}