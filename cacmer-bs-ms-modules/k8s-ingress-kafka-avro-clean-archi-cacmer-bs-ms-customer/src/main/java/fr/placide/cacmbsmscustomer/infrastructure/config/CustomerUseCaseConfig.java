package fr.placide.cacmbsmscustomer.infrastructure.config;

import fr.placide.cacmbsmscustomer.domain.outputport.*;
import fr.placide.cacmbsmscustomer.domain.usecase.CustomerInputServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerUseCaseConfig {
    @Bean
    CustomerInputServiceImpl config(CustomerProducerService producerService,
                                    CustomerOutputService outputService,
                                    RemoteAddressOutputService remoteAddress,
                                    RemoteAccountOutputService remoteAccount) {
        return new CustomerInputServiceImpl(producerService, outputService, remoteAddress, remoteAccount);
    }
}
