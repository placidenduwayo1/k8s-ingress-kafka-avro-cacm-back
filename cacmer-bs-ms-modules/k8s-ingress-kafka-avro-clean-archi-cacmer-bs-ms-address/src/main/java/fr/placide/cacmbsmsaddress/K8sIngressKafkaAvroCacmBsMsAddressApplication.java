package fr.placide.cacmbsmsaddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class K8sIngressKafkaAvroCacmBsMsAddressApplication {

	public static void main(String[] args) {
		new SpringApplication(K8sIngressKafkaAvroCacmBsMsAddressApplication.class)
				.run(args);
	}

}
