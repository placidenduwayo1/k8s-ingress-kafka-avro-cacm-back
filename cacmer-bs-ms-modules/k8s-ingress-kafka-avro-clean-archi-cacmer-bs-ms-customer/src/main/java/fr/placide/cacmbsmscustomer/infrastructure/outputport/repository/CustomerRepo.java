package fr.placide.cacmbsmscustomer.infrastructure.outputport.repository;

import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerModel, String> {
    List<CustomerModel> findByLastname(String lastname);
    List<CustomerModel> findByFirstnameAndLastname(String firstname, String lastname);
    List<CustomerModel> findByAddressId(String addressId);
}
