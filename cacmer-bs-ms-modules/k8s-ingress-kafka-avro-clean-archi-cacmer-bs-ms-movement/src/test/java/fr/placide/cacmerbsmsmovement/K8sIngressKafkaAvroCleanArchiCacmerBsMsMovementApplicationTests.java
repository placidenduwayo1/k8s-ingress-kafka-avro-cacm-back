package fr.placide.cacmerbsmsmovement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class K8sIngressKafkaAvroCleanArchiCacmerBsMsMovementApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertNotNull(this.getClass().getSimpleName());
	}

}
