package fr.placide.cacmerbsmsmovement.infrastructure.outputport.repository;

import fr.placide.cacmerbsmsmovement.infrastructure.outputport.models.MovementModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepo extends JpaRepository<MovementModel, String> {
    List<MovementModel> findByAccountId(String accountId);
}
