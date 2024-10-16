package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Food;

/**
 * FoodRepository
 **/
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    Food findByBrief(String brief);

}
