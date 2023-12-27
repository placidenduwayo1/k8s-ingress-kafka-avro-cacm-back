package fr.placide.cacmerbsmsaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class K8sIngressKafkaAvroCleanArchiCacmerBsMsAccountApplication {

    public static void main(String[] args) {
       new SpringApplication(K8sIngressKafkaAvroCleanArchiCacmerBsMsAccountApplication.class)
               .run(args);
    }
}
