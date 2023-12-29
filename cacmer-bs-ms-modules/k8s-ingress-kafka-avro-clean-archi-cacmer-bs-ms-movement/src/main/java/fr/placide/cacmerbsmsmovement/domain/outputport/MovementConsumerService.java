package fr.placide.cacmerbsmsmovement.domain.outputport;

import fr.placide.cacmerbsmsmovement.domain.avro.MovementAvro;
import fr.placide.cacmerbsmsmovement.domain.beans.Movement;

public interface MovementConsumerService {
    Movement consumeMovementCreateEvent(MovementAvro avro, String topic);
    Movement consumeMovementEditEvent(MovementAvro avro, String topic);
    Movement consumeMovementDeleteEvent(MovementAvro avro, String topic);
}
