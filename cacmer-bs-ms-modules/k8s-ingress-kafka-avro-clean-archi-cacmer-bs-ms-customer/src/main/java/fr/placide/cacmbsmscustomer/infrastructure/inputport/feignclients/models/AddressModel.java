package fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models;

import lombok.Builder;
import lombok.Data;

@Builder@Data
public class AddressModel {
    private String addressId;
    private int num;
    private String street;
    private int pb;
    private String city;
    private String country;
}
