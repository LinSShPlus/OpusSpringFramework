package ru.otus.spring.service;

import ru.otus.spring.domain.Food;

import java.util.List;
import java.util.Optional;

/**
 * FoodService
 **/
public interface FoodService {

    Food save(Food food);

    void deleteById(long id);

    Optional<Food> getById(long id);

    Food getByBrief(String brief);

    List<Food> getAll();

}
