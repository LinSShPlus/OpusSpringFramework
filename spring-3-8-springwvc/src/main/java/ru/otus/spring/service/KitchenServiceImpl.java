package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Food;
import ru.otus.spring.domain.FoodWithSauce;
import ru.otus.spring.domain.Order;
import ru.otus.spring.domain.Sauce;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * KitchenServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class KitchenServiceImpl implements KitchenService {

    private final FoodService foodService;
    private final SauceService sauceService;

    @Override
    public FoodWithSauce getFoodWithSauce(Order order) {
        Food food = foodService.getByBrief(order.getFoodBrief());
        Sauce sauce = order.isRandomSauce() ? getRandomSauce() : sauceService.getByBrief(order.getSauceBrief());
        return FoodWithSauce
                .builder()
                .food(food)
                .sauce(sauce)
                .build();
    }

    private Sauce getRandomSauce() {
        List<Sauce> sauces = sauceService.getAll();
        return sauces.get(ThreadLocalRandom.current().nextInt(0, sauces.size()));
    }

}
