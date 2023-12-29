package fr.placide.cacmerbsmsmovement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class K8sIngressKafkaAvroCleanArchiCacmerBsMsMovementApplication {

	public static void main(String[] args) {
		new SpringApplication(K8sIngressKafkaAvroCleanArchiCacmerBsMsMovementApplication.class)
				.run(args);
	}

}
