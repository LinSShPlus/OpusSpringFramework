package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * AuthorService
 **/
public interface AuthorService {

    long save(Author author);

    int deleteById(long id);

    Optional<Author> getById(long id);

    Author getByBrief(String brief);

    List<Author> getAll();

}
