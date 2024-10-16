package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.FoodRepository;
import ru.otus.spring.domain.Food;

import java.util.List;
import java.util.Optional;

/**
 * FoodServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Transactional
    @Override
    public Food save(Food food) {
        return foodRepository.save(food);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        foodRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Food> getById(long id) {
        return foodRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Food getByBrief(String brief) {
        return foodRepository.findByBrief(brief);
    }

    @Override
    public List<Food> getAll() {
        return foodRepository.findAll();
    }

}
