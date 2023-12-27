package fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder@Getter@Setter
public class CustomerModel {
    private String customerId;
    private String addressId;
}
