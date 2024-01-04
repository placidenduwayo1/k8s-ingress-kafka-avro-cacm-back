package fr.placide.cacmerriskevaluator.infrastruture.feignclients.mapper;

import fr.placide.cacmerriskevaluator.domain.beans.Account;
import fr.placide.cacmerriskevaluator.domain.beans.Customer;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.AccountModel;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.CustomerModel;
import org.springframework.beans.BeanUtils;

public class Mapper {
    private Mapper(){}
    public static Account map(AccountModel model){
        Account bean = new Account.AccountBuilder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
    public static Customer map(CustomerModel model){
        Customer bean = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
}
