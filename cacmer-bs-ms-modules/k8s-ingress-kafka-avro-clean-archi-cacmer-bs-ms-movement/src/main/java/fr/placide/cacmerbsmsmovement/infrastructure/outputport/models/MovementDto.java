package fr.placide.cacmerbsmsmovement.infrastructure.outputport.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder@Setter@Getter
public class MovementDto {
    private String sens;
    private double amount;
    private String accountId;
}
