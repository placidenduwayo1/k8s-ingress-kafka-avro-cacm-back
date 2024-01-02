package fr.placide.cacmerriskevaluator.infrastruture.config;

import fr.placide.cacmerriskevaluator.domain.usecase.RiskEvaluatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RiskEvaluatorConfig {
    @Bean
    public RiskEvaluatorImpl config(){
        return new RiskEvaluatorImpl();
    }
}
