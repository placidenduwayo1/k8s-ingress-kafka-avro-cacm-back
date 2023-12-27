package fr.placide.cacmerbsmsaccount.infrastructure.outputport.repository;

import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepo extends JpaRepository<AccountModel, String> {
    AccountModel findByCustomerId(String customerId);
}
