package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * GenreService
 **/
public interface GenreService {

    Genre save(Genre genre);

    void deleteById(long id);

    Optional<Genre> getById(long id);

    Genre getByBrief(String brief);

    List<Genre> getAll();

}
