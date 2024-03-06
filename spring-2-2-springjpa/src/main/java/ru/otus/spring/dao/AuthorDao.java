package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * AuthorDao
 **/
public interface AuthorDao {

    Author save(Author author);

    int deleteById(long id);

    Optional<Author> findById(long id);

    Author findByBrief(String brief);

    List<Author> findAll();

}
