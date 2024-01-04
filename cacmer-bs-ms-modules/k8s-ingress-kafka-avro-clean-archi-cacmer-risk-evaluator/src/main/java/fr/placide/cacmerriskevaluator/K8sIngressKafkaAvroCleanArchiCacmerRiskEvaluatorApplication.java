package fr.placide.cacmerriskevaluator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class K8sIngressKafkaAvroCleanArchiCacmerRiskEvaluatorApplication {

	public static void main(String[] args) {
		new SpringApplication(K8sIngressKafkaAvroCleanArchiCacmerRiskEvaluatorApplication.class)
				.run(args);
	}

}
