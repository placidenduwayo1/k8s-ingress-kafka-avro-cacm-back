package fr.placide.cacmbsmsaddress.infrastructure.outputport.mapper;

import fr.placide.cacmbsmsaddress.domain.avro.AddressAvro;
import fr.placide.cacmbsmsaddress.domain.beans.Address;
import fr.placide.cacmbsmsaddress.domain.beans.Customer;
import fr.placide.cacmbsmsaddress.infrastructure.inputport.feignclients.model.CustomerModel;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressModel;
import org.springframework.beans.BeanUtils;

public class Mapper {
    private Mapper(){}
    public static Address map(AddressModel model){
        Address bean = new Address.AddressBuilder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
    public static AddressModel map(Address bean){
        AddressModel model = AddressModel.builder().build();
        BeanUtils.copyProperties(bean,model);
        return model;
    }
    public static Address map(AddressDto dto){
        Address bean = new Address.AddressBuilder().build();
        BeanUtils.copyProperties(dto,bean);
        return bean;
    }
    public static AddressAvro toAvro(Address address){
        return AddressAvro.newBuilder()
                .setAddressId(address.getAddressId())
                .setNum(address.getNum())
                .setStreet(address.getStreet())
                .setPb(address.getPb())
                .setCity(address.getCity())
                .setCountry(address.getCountry())
                .build();
    }
    public static Address map(AddressAvro avro){
        return new Address.AddressBuilder()
                .addressId(avro.getAddressId())
                .num(avro.getNum())
                .street(avro.getStreet())
                .pb(avro.getPb())
                .city(avro.getCity())
                .country(avro.getCountry())
                .build();
    }
    public static Customer map(CustomerModel model){
       return new Customer.CustomerBuilder()
               .customerId(model.getCustomerId())
               .build();

    }
}
