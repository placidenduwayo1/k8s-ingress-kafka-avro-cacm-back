package fr.placide.cacmbsmscustomer.infrastructure.outputport.models;

import lombok.Builder;
import lombok.Data;
@Builder@Data
public class CustomerDto {
    private String firstname;
    private String lastname;
    private String createdAt;
    private String risk;
    private String status;
    private String addressId;
}
