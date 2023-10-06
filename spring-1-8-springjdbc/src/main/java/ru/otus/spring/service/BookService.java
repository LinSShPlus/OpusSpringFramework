package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

/**
 * BookService
 **/
public interface BookService {

    long insert(Book book);

    int update(Book book);

    int deleteById(long id);

    Book getById(long id);

    Book getByBrief(String brief);

    List<Book> getAll();

    int count();

}
