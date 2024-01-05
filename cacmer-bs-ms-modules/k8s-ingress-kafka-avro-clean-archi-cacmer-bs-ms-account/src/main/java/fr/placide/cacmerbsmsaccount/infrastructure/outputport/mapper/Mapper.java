package fr.placide.cacmerbsmsaccount.infrastructure.outputport.mapper;

import fr.placide.cacmerbsmsaccount.domain.avro.AccountAvro;
import fr.placide.cacmerbsmsaccount.domain.beans.Account;
import fr.placide.cacmerbsmsaccount.domain.beans.Customer;
import fr.placide.cacmerbsmsaccount.infrastructure.inputport.feignclients.models.CustomerModel;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountDto;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountModel;
import org.springframework.beans.BeanUtils;

public class Mapper {
    private Mapper() {
    }

    public static Account map(AccountModel model) {
        Account account = new Account.AccountBuilder().build();
        BeanUtils.copyProperties(model, account);
        return account;
    }

    public static AccountModel map(Account bean) {
        AccountModel model = AccountModel.builder().build();
        BeanUtils.copyProperties(bean, model);
        return model;
    }

    public static Customer map(CustomerModel model) {
        Customer bean = Customer.builder().build();
        BeanUtils.copyProperties(model, bean);
        return bean;
    }

    public static Account map(AccountDto dto) {
        Account bean = new Account.AccountBuilder().build();
        BeanUtils.copyProperties(dto, bean);
        return bean;
    }

    public static Account map(AccountAvro avro) {
        Customer customer = Customer.builder()
                .customerId(avro.getCustomerId())
                .firstname(avro.getCustomer().getFirstname())
                .lastname(avro.getCustomer().getLastname())
                .createdAt(avro.getCustomer().getCreatedAt())
                .risk(avro.getCustomer().getRisk())
                .status(avro.getCustomer().getStatus())
                .build();
        return new Account.AccountBuilder()
                .accountId(avro.getAccountId())
                .type(avro.getType())
                .balance(avro.getBalance())
                .overdraft(avro.getOverdraft())
                .customerId(avro.getCustomerId())
                .customer(customer)
                .build();
    }
    public static AccountAvro toAvro(Account bean){
        fr.placide.cacmerbsmsaccount.domain.avro.Customer customer = fr.placide.cacmerbsmsaccount.domain.avro.Customer.newBuilder()
                .setCustomerId(bean.getCustomerId())
                .setFirstname(bean.getCustomer().getFirstname())
                .setLastname(bean.getCustomer().getLastname())
                .setCreatedAt(bean.getCustomer().getCreatedAt())
                .setRisk(bean.getCustomer().getRisk())
                .setStatus(bean.getCustomer().getStatus())
                .build();
        return AccountAvro.newBuilder()
                .setAccountId(bean.getAccountId())
                .setType(bean.getType())
                .setBalance(bean.getBalance())
                .setOverdraft(bean.getOverdraft())
                .setCustomerId(bean.getCustomerId())
                .setCustomer(customer)
                .build();
    }

    public static CustomerModel map(Customer bean) {
        CustomerModel model = CustomerModel.builder().build();
        BeanUtils.copyProperties(bean, model);
        return model;
    }
}
