package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.FoodWithSauce;
import ru.otus.spring.domain.Order;
import ru.otus.spring.integration.KitchenGateway;

/**
 * KitchenController
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class KitchenController {

    private final KitchenGateway kitchenGateway;

    @PostMapping(value = "/kitchen")
    public ResponseEntity<FoodWithSauce> getFoodWithSauce(@RequestBody Order order) {
        FoodWithSauce foodWithSauce = kitchenGateway.process(order);
        return ResponseEntity.ok(foodWithSauce);
    }

}
