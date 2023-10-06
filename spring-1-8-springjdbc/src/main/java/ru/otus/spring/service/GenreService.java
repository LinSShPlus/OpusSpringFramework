package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;

/**
 * GenreService
 **/
public interface GenreService {

    long insert(Genre genre);

    int update(Genre genre);

    int deleteById(long id);

    Genre getById(long id);

    Genre getByBrief(String brief);

    List<Genre> getAll();

}
