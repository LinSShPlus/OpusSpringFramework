package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.otus.spring.domain.Food;
import ru.otus.spring.domain.FoodWithSauce;
import ru.otus.spring.domain.Order;
import ru.otus.spring.domain.Sauce;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Класс KitchenServiceImpl")
@ExtendWith(MockitoExtension.class)
class KitchenServiceImplTest {

    @Mock
    private FoodService foodService;

    @Mock
    private SauceService sauceService;

    @InjectMocks
    private KitchenServiceImpl kitchenService;


    @DisplayName("Заказ с выбранной едой и соусом")
    @Test
    void getFoodWithSauce_ValidOrder_ReturnsFoodWithSauce() {
        Order order = new Order("foodBrief", "sauceBrief", false);
        Food food = createFood();
        Sauce sauce = createSauce();
        when(foodService.getByBrief(order.getFoodBrief())).thenReturn(food);
        when(sauceService.getByBrief(order.getSauceBrief())).thenReturn(sauce);

        FoodWithSauce result = kitchenService.getFoodWithSauce(order);

        assertNotNull(result);
        assertEquals(food, result.getFood());
        assertEquals(sauce, result.getSauce());
    }

    @DisplayName("Заказ с выбранной едой и случайным соусом")
    @Test
    void getFoodWithSauce_OrderWithRandomSauce_ReturnsFoodWithRandomSauce() {
        Order order = new Order("foodBrief", "sauceBrief", true);
        Food food = createFood();
        Sauce sauce = createSauce();
        when(foodService.getByBrief(order.getFoodBrief())).thenReturn(food);
        when(sauceService.getAll()).thenReturn(List.of(sauce));

        FoodWithSauce result = kitchenService.getFoodWithSauce(order);

        assertNotNull(result);
        assertEquals(food, result.getFood());
        assertEquals(sauce, result.getSauce());
    }

    @DisplayName("Нулевой заказ")
    @Test
    void getFoodWithSauce_InvalidOrder_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> kitchenService.getFoodWithSauce(null));
    }

    private Food createFood() {
        return Food.builder()
                .brief("foodBrief")
                .name("foodName")
                .build();
    }

    private Sauce createSauce() {
        return Sauce.builder()
                .brief("sauceBrief")
                .name("sauceName")
                .build();
    }

}
