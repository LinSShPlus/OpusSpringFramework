package ru.otus.spring.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

/**
 * IntegrationConfig
 **/
@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel orderChannel() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public MessageChannel kitchenChannel() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public IntegrationFlow kitchenFlow() {
        return IntegrationFlows
                .from("orderChannel")
                .handle("kitchenServiceImpl", "getFoodWithSauce")
                .channel("kitchenChannel")
                .get();
    }

}
