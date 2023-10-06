package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;

/**
 * AuthorService
 **/
public interface AuthorService {

    long insert(Author author);

    int update(Author author);

    int deleteById(long id);

    Author getById(long id);

    Author getByBrief(String brief);

    List<Author> getAll();

}
