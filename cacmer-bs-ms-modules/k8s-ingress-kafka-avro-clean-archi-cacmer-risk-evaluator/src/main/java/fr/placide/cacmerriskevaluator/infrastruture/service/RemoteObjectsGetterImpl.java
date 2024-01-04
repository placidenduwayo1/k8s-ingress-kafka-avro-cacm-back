package fr.placide.cacmerriskevaluator.infrastruture.service;

import fr.placide.cacmerriskevaluator.domain.beans.Account;
import fr.placide.cacmerriskevaluator.domain.beans.Customer;
import fr.placide.cacmerriskevaluator.domain.outputport.RemoteObjectsGetter;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.mapper.Mapper;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.AccountModel;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.CustomerModel;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.proxies.AccountServiceProxy;
import fr.placide.cacmerriskevaluator.infrastruture.feignclients.proxies.CustomerServiceProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RemoteObjectsGetterImpl implements RemoteObjectsGetter {
    private final AccountServiceProxy accountServiceProxy;
    private final CustomerServiceProxy customerServiceProxy;

    public RemoteObjectsGetterImpl(@Qualifier(value = "accountserviceproxy") AccountServiceProxy accountServiceProxy,
                                   @Qualifier(value = "customerserviceproxy") CustomerServiceProxy customerServiceProxy) {
        this.accountServiceProxy = accountServiceProxy;
        this.customerServiceProxy = customerServiceProxy;
    }

    @Override
    public Account getRemoteAccountById(String id) {
        AccountModel model = accountServiceProxy.getRemoteAccountById(id);
        Account bean = null;
        if(model!=null){
            bean = Mapper.map(model);
        }
        return bean;
    }

    @Override
    public Customer getRemoteCustomerById(String id) {
        CustomerModel model = customerServiceProxy.getRemoteCustomerById(id);
        Customer bean  = null;
        if(model!=null){
            bean = Mapper.map(model);
        }
        return bean;
    }
}
