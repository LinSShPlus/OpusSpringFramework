package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;

/**
 * GenreDao
 **/
public interface GenreDao {

    long insert(Genre genre);

    int update(Genre genre);

    int deleteById(long id);

    Genre getById(long id);

    Genre getByBrief(String brief);

    List<Genre> getAll();

}
