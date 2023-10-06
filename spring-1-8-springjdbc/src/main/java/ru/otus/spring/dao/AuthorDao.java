package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

/**
 * AuthorDao
 **/
public interface AuthorDao {

    long insert(Author author);

    int update(Author author);

    int deleteById(long id);

    Author getById(long id);

    Author getByBrief(String brief);

    List<Author> getAll();

}
