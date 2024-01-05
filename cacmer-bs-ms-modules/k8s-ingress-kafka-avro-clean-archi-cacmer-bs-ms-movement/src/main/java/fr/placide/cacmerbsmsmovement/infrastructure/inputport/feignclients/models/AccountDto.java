package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class AccountDto {
    private String type;
    private double balance;
    private double overdraft;
    private String customerId;
}
