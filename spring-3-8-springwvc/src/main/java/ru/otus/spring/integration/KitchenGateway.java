package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.domain.FoodWithSauce;
import ru.otus.spring.domain.Order;

/**
 * KitchenGateway
 **/
@MessagingGateway
public interface KitchenGateway {

    @Gateway(requestChannel = "orderChannel", replyChannel = "kitchenChannel")
    FoodWithSauce process(Order order);

}
