package fr.placide.cacmbsmscustomer.infrastructure.config;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerOutputService;
import fr.placide.cacmbsmscustomer.domain.outputport.CustomerProducerService;
import fr.placide.cacmbsmscustomer.domain.outputport.RemoteAddressOutputService;
import fr.placide.cacmbsmscustomer.domain.usecase.CustomerInputServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerUseCaseConfig {
    @Bean
    CustomerInputServiceImpl config(CustomerProducerService producerService,
                                    CustomerOutputService outputService,
                                    RemoteAddressOutputService remote){
        return new CustomerInputServiceImpl(producerService, outputService,remote);
    }
}
