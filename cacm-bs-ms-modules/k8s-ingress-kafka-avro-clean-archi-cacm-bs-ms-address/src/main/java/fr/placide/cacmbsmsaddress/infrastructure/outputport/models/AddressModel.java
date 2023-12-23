package fr.placide.cacmbsmsaddress.infrastructure.outputport.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Builder@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
@Entity@Table(name = "addresses")
public class AddressModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String addressId;
    private int num;
    private String street;
    private int pb;
    private String city;
    private String country;
}
