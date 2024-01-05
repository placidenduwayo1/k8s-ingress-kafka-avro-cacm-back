package fr.placide.cacmerbsmsmovement.infrastructure.config;

import fr.placide.cacmerbsmsmovement.domain.outputport.*;
import fr.placide.cacmerbsmsmovement.domain.usecase.MovementInputServiceImpl;
import fr.placide.cacmerbsmsmovement.infrastructure.inputport.feignclients.proxies.RiskEvaluatorServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvtUseCaseConf {
    @Bean
    public MovementInputServiceImpl config(RemoteAccountService remoteAccount, RemoteCustomerService remoteCustomer,
                                           MovementProducerService produceService, MovementOutputService outputService,
                                           RemoteRiskEvaluatorService remoteRiskEvaluatorService){
        return new MovementInputServiceImpl(remoteAccount,remoteCustomer,produceService,outputService, remoteRiskEvaluatorService);
    }
}
