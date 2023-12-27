package fr.placide.cacmbsmscustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class K8sIngressKafkaAvroCleanArchiCacmBsMsCustomerApplication {
    public static void main(String[] args) {
        new SpringApplication(K8sIngressKafkaAvroCleanArchiCacmBsMsCustomerApplication.class)
                .run(args);
    }

}
