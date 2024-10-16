package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Food;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FoodRepositoryTest
 **/
@DisplayName("Класс FoodRepository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
class FoodRepositoryTest {

    private static final long EXPECTED_FOOD_ID = 1L;

    @Autowired
    private FoodRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить еду")
    @Test
    void insert() {
        Food food = getNewFood();
        Long id = repository.save(food).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить еду")
    @Test
    void update() {
        Food expectedFood = getExistFood();
        Food actualFood = repository.save(expectedFood);
        assertThat(actualFood).usingRecursiveComparison().isEqualTo(expectedFood);
    }

    @DisplayName("Удалить еду по идентификатору")
    @Test
    void deleteById() {
        repository.deleteById(EXPECTED_FOOD_ID);
        em.flush();
        Food actualFood = em.find(Food.class, EXPECTED_FOOD_ID);
        assertThat(actualFood).isNull();
    }

    @DisplayName("Вернуть еду по идентификатору")
    @Test
    void findById() {
        Food expectedFood = getExistFood();
        Optional<Food> optionalActualSauce = repository.findById(expectedFood.getId());
        assertThat(optionalActualSauce).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedFood));
    }

    @DisplayName("Вернуть еду по сокращению")
    @Test
    void findByBrief() {
        Food expectedFood = getExistFood();
        Food actualFood = repository.findByBrief(expectedFood.getBrief());
        assertThat(actualFood).isNotNull().usingRecursiveComparison().isEqualTo(expectedFood);
    }

    @DisplayName("Вернуть всю еду")
    @Test
    void findAll() {
        List<Food> foods = repository.findAll();
        assertThat(foods).size().isPositive();
    }

    private Food getNewFood() {
        return Food
                .builder()
                .brief("brief")
                .name("name")
                .build();
    }

    private Food getExistFood() {
        return em.find(Food.class, EXPECTED_FOOD_ID);
    }

}