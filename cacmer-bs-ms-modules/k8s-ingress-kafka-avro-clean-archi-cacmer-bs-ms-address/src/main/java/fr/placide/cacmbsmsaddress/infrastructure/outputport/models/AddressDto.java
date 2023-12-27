package fr.placide.cacmbsmsaddress.infrastructure.outputport.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class AddressDto {
    private int num;
    private String street;
    private int pb;
    private String city;
    private String country;
}
