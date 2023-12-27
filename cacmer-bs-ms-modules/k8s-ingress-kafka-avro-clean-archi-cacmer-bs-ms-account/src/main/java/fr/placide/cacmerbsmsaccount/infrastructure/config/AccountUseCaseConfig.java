package fr.placide.cacmerbsmsaccount.infrastructure.config;

import fr.placide.cacmerbsmsaccount.domain.outputport.AccountOutputService;
import fr.placide.cacmerbsmsaccount.domain.outputport.AccountProducerService;
import fr.placide.cacmerbsmsaccount.domain.outputport.RemoteCustomerOutputService;
import fr.placide.cacmerbsmsaccount.domain.usecase.AccountInputServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountUseCaseConfig {
    @Bean
    AccountInputServiceImpl config(RemoteCustomerOutputService remoteCustomerService,
                                   AccountOutputService accountOutputService,
                                   AccountProducerService accountProducerService){
        return new AccountInputServiceImpl(remoteCustomerService,accountOutputService,accountProducerService);
    }
}
