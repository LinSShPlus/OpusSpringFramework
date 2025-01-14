package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

/**
 * GenreRepository
 **/
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByBrief(String brief);

}
