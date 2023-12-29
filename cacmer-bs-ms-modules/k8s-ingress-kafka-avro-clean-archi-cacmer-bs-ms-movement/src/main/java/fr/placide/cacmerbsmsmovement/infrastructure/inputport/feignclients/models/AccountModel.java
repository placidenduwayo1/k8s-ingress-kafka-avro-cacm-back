package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AccountModel {
    private String accountId;
    private String type;
    private double balance;
    private double overdraft;
    private String customerId;
    private CustomerModel customerModel;
}
