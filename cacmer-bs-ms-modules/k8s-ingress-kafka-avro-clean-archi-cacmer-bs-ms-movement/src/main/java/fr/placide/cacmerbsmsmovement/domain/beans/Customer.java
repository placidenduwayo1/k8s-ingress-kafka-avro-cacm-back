package fr.placide.cacmerbsmsmovement.domain.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Setter @Getter
public class Customer {
    private String customerId;
    private String firstname;
    private String lastname;
    private String createdAt;
    private String risk;
    private String status;
}
