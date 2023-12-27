package fr.placide.cacmerbsmsaccount.domain.outputport;


import fr.placide.cacmerbsmsaccount.domain.avro.AccountAvro;
import fr.placide.cacmerbsmsaccount.domain.beans.Account;

public interface AccountConsumerService {
    Account consumeAccountCreateEvent(AccountAvro avro, String topic);
    Account consumeAccountEditEvent(AccountAvro avro, String topic);
    Account consumeAccountDeleteEvent(AccountAvro avro, String topic);
}
