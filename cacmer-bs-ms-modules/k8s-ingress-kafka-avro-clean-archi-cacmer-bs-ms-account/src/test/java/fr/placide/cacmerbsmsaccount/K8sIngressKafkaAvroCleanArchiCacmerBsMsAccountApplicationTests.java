package fr.placide.cacmerbsmsaccount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
class K8sIngressKafkaAvroCleanArchiCacmerBsMsAccountApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertNotNull(this.getClass().getSimpleName());
    }

}
