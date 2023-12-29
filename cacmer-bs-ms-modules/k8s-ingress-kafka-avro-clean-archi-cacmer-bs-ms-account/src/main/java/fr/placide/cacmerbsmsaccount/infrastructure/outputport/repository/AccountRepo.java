package fr.placide.cacmerbsmsaccount.infrastructure.outputport.repository;

import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AccountRepo extends JpaRepository<AccountModel, String> {
    List<AccountModel> findByCustomerId(String customerId);
}
