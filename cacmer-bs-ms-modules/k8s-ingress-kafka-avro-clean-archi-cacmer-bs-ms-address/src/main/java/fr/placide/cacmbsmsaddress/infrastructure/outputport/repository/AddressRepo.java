package fr.placide.cacmbsmsaddress.infrastructure.outputport.repository;

import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<AddressModel, String> {
    List<AddressModel> findByCity(String city);
    List<AddressModel> findByNumAndStreetAndPbAndCityAndCountry(int num, String street, int pb,String city, String country);
}
