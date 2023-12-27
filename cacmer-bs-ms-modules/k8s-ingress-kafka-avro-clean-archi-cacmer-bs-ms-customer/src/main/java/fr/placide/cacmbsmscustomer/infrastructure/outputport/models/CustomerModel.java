package fr.placide.cacmbsmscustomer.infrastructure.outputport.models;

import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AddressModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder @AllArgsConstructor @NoArgsConstructor @Data
@Entity@Table(name = "customers")
public class CustomerModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String customerId;
    private String firstname;
    private String lastname;
    private String createdAt;
    private String risk;
    private String status;
    private String addressId;
    @Transient
    private AddressModel model;
}
