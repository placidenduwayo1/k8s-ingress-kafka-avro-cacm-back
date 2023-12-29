package fr.placide.cacmerbsmsmovement.infrastructure.outputport.mapper;

import fr.placide.cacmerbsmsmovement.domain.avro.MovementAvro;
import fr.placide.cacmerbsmsmovement.domain.beans.Account;
import fr.placide.cacmerbsmsmovement.domain.beans.Customer;
import fr.placide.cacmerbsmsmovement.domain.beans.Movement;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.AccountModel;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.models.CustomerModel;
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.models.MovementDto;
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.models.MovementModel;
import org.springframework.beans.BeanUtils;

public class Mapper {
    private Mapper(){}
    public static Account map(AccountModel model){
        Account bean  = Account.builder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
    public static Customer map(CustomerModel model){
        Customer bean = Customer.builder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
    public static Movement map(MovementModel model){
        Movement bean = new Movement.MovementBuilder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
    public static Movement map(MovementDto dto){
        Movement bean = new Movement.MovementBuilder().build();
        BeanUtils.copyProperties(dto,bean);
        return bean;
    }
    public static MovementAvro toAvro(Movement bean){
        fr.placide.cacmerbsmsmovement.domain.avro.Customer customer = fr.placide.cacmerbsmsmovement.domain.avro.Customer.newBuilder()
                .setCustomerId(bean.getCustomerId())
                .setFirstname(bean.getCustomer().getFirstname())
                .setLastname(bean.getCustomer().getLastname())
                .setCreatedAt(bean.getCustomer().getCreatedAt())
                .setRisk(bean.getCustomer().getRisk())
                .setStatus(bean.getCustomer().getStatus())
                .build();
        fr.placide.cacmerbsmsmovement.domain.avro.Account account = fr.placide.cacmerbsmsmovement.domain.avro.Account.newBuilder()
                .setAccountId(bean.getAccountId())
                .setType(bean.getAccount().getType())
                .setBalance(bean.getAccount().getBalance())
                .setOverdraft(bean.getAccount().getOverdraft())
                .setCustomer(customer)
                .build();

        return MovementAvro.newBuilder()
                .setMvtId(bean.getMvtId())
                .setSens(bean.getSens())
                .setAmount(bean.getAmount())
                .setCreatedAt(bean.getCreatedAt())
                .setAccount(account)
                .build();
    }

    public static Movement map(MovementAvro avro){
        Account account = Account.builder()
                .accountId(avro.getAccountId())
                .type(avro.getAccount().getType())
                .balance(avro.getAccount().getBalance())
                .overdraft(avro.getAccount().getOverdraft())
                .customerId(avro.getAccount().getCustomerId())
                .build();
        Customer customer = Customer.builder()
                .customerId(avro.getAccount().getCustomerId())
                .firstname(avro.getAccount().getCustomer().getFirstname())
                .lastname(avro.getAccount().getCustomer().getLastname())
                .createdAt(avro.getAccount().getCustomer().getCreatedAt())
                .risk(avro.getAccount().getCustomer().getRisk())
                .status(avro.getAccount().getCustomer().getStatus())
                .build();

       return new Movement.MovementBuilder()
               .mvtId(avro.getMvtId())
               .sens(avro.getSens())
               .amount(avro.getAmount())
               .createdAt(avro.getCreatedAt())
               .account(account)
               .customer(customer)
               .build();

    }

    public static MovementModel map(Movement bean){
        MovementModel model = MovementModel.builder().build();
        BeanUtils.copyProperties(bean,model);
        return model;
    }
}
