package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.otus.spring.domain.Food;
import ru.otus.spring.domain.FoodWithSauce;
import ru.otus.spring.domain.Order;
import ru.otus.spring.domain.Sauce;
import ru.otus.spring.integration.KitchenGateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * KitchenControllerTest
 **/
@DisplayName("Класс KitchenController")
@ExtendWith(MockitoExtension.class)
class KitchenControllerTest {

    @Mock
    private KitchenGateway kitchenGateway;

    @InjectMocks
    private KitchenController kitchenController;

    @DisplayName("Заказ с выбранной едой и соусом")
    @Test
    void getFoodWithSauce_ValidOrder_FoodAndSauceBriefs() {
        Order order = new Order("foodBrief", "sauceBrief", false);
        Food food = createFood();
        Sauce sauce = createSauce();
        FoodWithSauce expected = new FoodWithSauce(food, sauce);
        when(kitchenGateway.process(order)).thenReturn(expected);

        ResponseEntity<FoodWithSauce> result = kitchenController.getFoodWithSauce(order);

        assertEquals(expected, result.getBody());
    }

    @DisplayName("Заказ с выбранной едой и случайным соусом")
    @Test
    void getFoodWithSauce_ValidOrder_RandomSauce() {
        Order order = new Order("foodBrief", null, true);
        Food food = createFood();
        Sauce sauce = createSauce();
        FoodWithSauce expected = new FoodWithSauce(food, sauce);
        when(kitchenGateway.process(order)).thenReturn(expected);

        ResponseEntity<FoodWithSauce> result = kitchenController.getFoodWithSauce(order);

        assertEquals(expected, result.getBody());
    }

    private Food createFood() {
        return Food.builder()
                .brief("potato")
                .name("potato")
                .build();
    }

    private Sauce createSauce() {
        return Sauce.builder()
                .brief("ketchup")
                .name("ketchup")
                .build();
    }

}
