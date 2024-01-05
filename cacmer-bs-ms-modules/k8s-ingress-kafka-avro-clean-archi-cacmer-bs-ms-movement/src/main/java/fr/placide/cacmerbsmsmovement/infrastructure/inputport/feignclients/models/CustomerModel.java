package fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class CustomerModel {
    private String customerId;
    private String firstname;
    private String lastname;
    private String createdAt;
    private String risk;
    private String status;
}
