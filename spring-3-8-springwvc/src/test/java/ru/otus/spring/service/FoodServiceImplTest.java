package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.FoodRepository;
import ru.otus.spring.domain.Food;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * FoodServiceImplTest
 **/
@DisplayName("Класс FoodServiceImpl")
@ExtendWith(MockitoExtension.class)
class FoodServiceImplTest {

    private static final long EXPECTED_FOOD_ID = 1;

    @Mock
    private FoodRepository foodRepository;
    @InjectMocks
    private FoodServiceImpl foodService;

    @DisplayName("Должен сохранить еду")
    @Test
    void save() {
        Food food = createFood();
        when(foodRepository.save(food)).thenReturn(food);
        assertThat(foodService.save(food)).isEqualTo(food);
    }

    @DisplayName("Должен удалить еду по идентификатору")
    @Test
    void deleteById() {
        foodService.deleteById(EXPECTED_FOOD_ID);
        verify(foodRepository, times(1)).deleteById(EXPECTED_FOOD_ID);
    }

    @DisplayName("Должен получить еду по идентификатору")
    @Test
    void getById() {
        Optional<Food> food = Optional.of(createFood());
        when(foodRepository.findById(EXPECTED_FOOD_ID)).thenReturn(food);
        assertThat(foodService.getById(EXPECTED_FOOD_ID)).isEqualTo(food);
    }

    @DisplayName("Должен получить еду по сокращению")
    @Test
    void getByBrief() {
        Food food = createFood();
        when(foodRepository.findByBrief(food.getBrief())).thenReturn(food);
        assertThat(foodService.getByBrief(food.getBrief())).isEqualTo(food);
    }

    @DisplayName("Должен получить всю еду")
    @Test
    void getAll() {
        List<Food> foods = List.of(createFood());
        when(foodRepository.findAll()).thenReturn(foods);
        assertThat(foodService.getAll()).isEqualTo(foods);
    }

    private Food createFood() {
        return Food
                .builder()
                .id(EXPECTED_FOOD_ID)
                .brief("potato")
                .name("potato")
                .build();
    }

}
