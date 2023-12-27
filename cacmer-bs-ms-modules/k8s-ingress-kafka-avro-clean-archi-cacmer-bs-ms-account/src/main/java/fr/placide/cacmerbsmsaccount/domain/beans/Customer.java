package fr.placide.cacmerbsmsaccount.domain.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class Customer {
    private String customerId;
    private String firstname;
    private String lastname;
    private String createdAt;
    private String risk;
    private String status;
}
