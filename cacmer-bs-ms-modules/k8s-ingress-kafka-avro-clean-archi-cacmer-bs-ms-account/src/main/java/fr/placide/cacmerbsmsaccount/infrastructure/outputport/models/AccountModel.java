package fr.placide.cacmerbsmsaccount.infrastructure.outputport.models;

import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Builder @Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "accounts")
public class AccountModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String accountId;
    private String type;
    private double balance;
    private double overdraft;
    private String customerId;
    @Transient
    private Customer customer;
}
