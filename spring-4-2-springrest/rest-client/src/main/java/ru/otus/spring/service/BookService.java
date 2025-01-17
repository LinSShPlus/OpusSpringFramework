package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * BookService
 **/
public interface BookService {

    Book save(Book book);

    void deleteById(long id);

    Optional<Book> getById(long id);

    Book getByBrief(String brief);

    List<Book> getAll();

}
