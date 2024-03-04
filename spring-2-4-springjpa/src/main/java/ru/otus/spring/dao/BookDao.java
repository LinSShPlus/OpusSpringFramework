package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * BookDao
 **/
public interface BookDao {

    Book save(Book book);

    int deleteById(long id);

    Optional<Book> findById(long id);

    Book findByBrief(String brief);

    List<Book> findAll();

    long count();

}
