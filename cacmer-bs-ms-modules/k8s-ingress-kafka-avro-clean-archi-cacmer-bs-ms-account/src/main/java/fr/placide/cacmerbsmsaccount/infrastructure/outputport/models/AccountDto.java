package fr.placide.cacmerbsmsaccount.infrastructure.outputport.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Setter @Getter
public class AccountDto {
    private String type;
    private double balance;
    private double overdraft;
    private String customerId;
}
