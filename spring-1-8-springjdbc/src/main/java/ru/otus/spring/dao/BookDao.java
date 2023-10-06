package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

/**
 * BookDao
 **/
public interface BookDao {

    long insert(Book book);

    int update(Book book);

    int deleteById(long id);

    Book getById(long id);

    Book getByBrief(String brief);

    List<Book> getAll();

    int count();

}
