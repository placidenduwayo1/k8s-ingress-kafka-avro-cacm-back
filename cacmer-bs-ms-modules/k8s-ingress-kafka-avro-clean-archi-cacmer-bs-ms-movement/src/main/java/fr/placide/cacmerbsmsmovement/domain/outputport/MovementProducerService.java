package fr.placide.cacmerbsmsmovement.domain.outputport;

import fr.placide.cacmerbsmsmovement.domain.avro.MovementAvro;

public interface MovementProducerService {
    MovementAvro produceKafkaEventCreateMovement(MovementAvro avro);
    MovementAvro produceKafkaEventUpdateMovement(MovementAvro avro);
    MovementAvro produceKafkaEventDeleteMovement(MovementAvro avro);
}
