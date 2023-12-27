package fr.placide.cacmbsmsaddress.infrastructure.config;

import fr.placide.cacmbsmsaddress.domain.outputport.AddressOutputService;
import fr.placide.cacmbsmsaddress.domain.outputport.AddressProducerService;
import fr.placide.cacmbsmsaddress.domain.outputport.RemoteCustomerService;
import fr.placide.cacmbsmsaddress.domain.usecase.AddressInputServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AddressUseCaseConfig {
    @Bean
    AddressInputServiceImpl config(AddressProducerService producerService,
                                   AddressOutputService outputService,
                                   RemoteCustomerService remoteCustomerService) {
        return new AddressInputServiceImpl(producerService, outputService, remoteCustomerService);
    }
}
