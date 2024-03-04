package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * GenreDao
 **/
public interface GenreDao {

    Genre save(Genre genre);

    int deleteById(long id);

    Optional<Genre> findById(long id);

    Genre findByBrief(String brief);

    List<Genre> findAll();

}
