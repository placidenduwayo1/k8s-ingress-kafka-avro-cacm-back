package fr.placide.msconfigservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class K8sIngressCacmMsConfigServiceApplication {

	public static void main(String[] args) {
		new SpringApplication(K8sIngressCacmMsConfigServiceApplication.class)
				.run(args);
	}

}
