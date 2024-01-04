package fr.placide.cacmerriskevaluator.infrastruture.config;

import fr.placide.cacmerriskevaluator.domain.outputport.RemoteObjectsGetter;
import fr.placide.cacmerriskevaluator.domain.usecase.InputPortRiskEvaluatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RiskEvaluatorConfig {
    @Bean
    public InputPortRiskEvaluatorImpl config(RemoteObjectsGetter objectsGetter){
        return new InputPortRiskEvaluatorImpl(objectsGetter);
    }
}
