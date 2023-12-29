package fr.placide.cacmerbsmsmovement.infrastructure.outputport.models;

import fr.placide.cacmerbsmsmovement.domain.beans.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Builder@Setter@Getter@AllArgsConstructor@NoArgsConstructor
@Entity@Table(name = "movements")
public class MovementModel {
    @Id@GenericGenerator(name = "uuid")
    private String mvtId;
    private String sens;
    private double amount;
    private String createdAt;
    private String accountId;
    @Transient
    private Account account;
}
