package ru.otus.spring.service;

import ru.otus.spring.domain.FoodWithSauce;
import ru.otus.spring.domain.Order;

/**
 * KitchenService
 **/
public interface KitchenService {

    public FoodWithSauce getFoodWithSauce(Order order);

}
