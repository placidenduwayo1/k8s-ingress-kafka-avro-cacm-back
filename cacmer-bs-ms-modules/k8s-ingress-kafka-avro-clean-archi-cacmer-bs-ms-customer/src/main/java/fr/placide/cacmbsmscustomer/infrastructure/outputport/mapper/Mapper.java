package fr.placide.cacmbsmscustomer.infrastructure.outputport.mapper;

import fr.placide.cacmbsmscustomer.domain.avro.CustomerAvro;
import fr.placide.cacmbsmscustomer.domain.beans.Address;
import fr.placide.cacmbsmscustomer.domain.beans.Customer;
import fr.placide.cacmbsmscustomer.infrastructure.inputport.feignclients.models.AddressModel;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerDto;
import org.springframework.beans.BeanUtils;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerModel;

public class Mapper {
    private Mapper() {
    }

    public static Customer map(CustomerModel model) {
        Customer bean = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(model, bean);
        return bean;
    }

    public static CustomerModel map(Customer bean) {
        CustomerModel model = CustomerModel.builder().build();
        BeanUtils.copyProperties(bean, model);
        return model;
    }

    public static Address map(AddressModel model) {
        Address bean = Address.builder().build();
        BeanUtils.copyProperties(model, bean);
        return bean;
    }
    public static Customer map(CustomerDto dto){
        Customer bean = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(dto,bean);
        return bean;
    }

    public static CustomerAvro toAvro(Customer bean) {
       fr.placide.cacmbsmscustomer.domain.avro.Address address =
                fr.placide.cacmbsmscustomer.domain.avro.Address.newBuilder()
                        .setAddressId(bean.getAddressId())
                        .setNum(bean.getAddress().getNum())
                        .setStreet(bean.getAddress().getStreet())
                        .setPb(bean.getAddress().getPb())
                        .setCity(bean.getAddress().getCity())
                        .setCountry(bean.getAddress().getCountry())
                        .build();
       return CustomerAvro.newBuilder()
                .setCustomerId(bean.getCustomerId())
                .setFirstname(bean.getFirstname())
                .setLastname(bean.getLastname())
                .setCreatedAt(bean.getCreatedAt())
                .setRisk(bean.getRisk())
                .setStatus(bean.getStatus())
                .setAddressId(bean.getAddressId())
                .setAddress(address)
                .build();
    }
    public static Customer map(CustomerAvro avro){
        Address address = Address.builder()
                .addressId(avro.getAddressId())
                .num(avro.getAddress().getNum())
                .street(avro.getAddress().getStreet())
                .pb(avro.getAddress().getPb())
                .city(avro.getAddress().getCity())
                .country(avro.getAddress().getCountry())
                .build();
        return new Customer.CustomerBuilder()
                .customerId(avro.getCustomerId())
                .firstname(avro.getFirstname())
                .lastname(avro.getLastname())
                .createdAt(avro.getCreatedAt())
                .risk(avro.getRisk())
                .status(avro.getStatus())
                .addressId(avro.getAddressId())
                .address(address)
                .build();
    }
}
