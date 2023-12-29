package fr.placide.cacmerbsmsmovement.domain.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class Account {
    private String accountId;
    private String type;
    private double balance;
    private double overdraft;
    private String customerId;
}
