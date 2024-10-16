package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Sauce;

import java.util.List;

/**
 * SauceRepository
 **/
@Repository
public interface SauceRepository extends JpaRepository<Sauce, Long> {

    Sauce findByBrief(String brief);

}
