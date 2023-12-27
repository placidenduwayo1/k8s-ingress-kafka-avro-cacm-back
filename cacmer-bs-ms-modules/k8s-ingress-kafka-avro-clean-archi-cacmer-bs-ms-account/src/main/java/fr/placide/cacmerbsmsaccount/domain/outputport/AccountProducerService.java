package fr.placide.cacmerbsmsaccount.domain.outputport;


import fr.placide.cacmerbsmsaccount.domain.avro.AccountAvro;

public interface AccountProducerService {
    AccountAvro produceKafkaEventCreateAccount(AccountAvro avro);
    AccountAvro produceKafkaEventUpdateAccount(AccountAvro avro);
    AccountAvro produceKafkaEventDeleteAccount(AccountAvro avro);

}
