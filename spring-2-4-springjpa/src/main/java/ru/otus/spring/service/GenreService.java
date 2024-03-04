package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * GenreService
 **/
public interface GenreService {

    long save(Genre genre);

    int deleteById(long id);

    Optional<Genre> getById(long id);

    Genre getByBrief(String brief);

    List<Genre> getAll();

}
