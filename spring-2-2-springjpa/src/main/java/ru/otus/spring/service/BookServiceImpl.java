package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import java.util.List;

/**
 * BookServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public long insert(Book book) {
        return bookDao.insert(book);
    }

    @Override
    public int update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public int deleteById(long id) {
        return bookDao.deleteById(id);
    }

    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public Book getByBrief(String brief) {
        return bookDao.getByBrief(brief);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public int count() {
        return bookDao.count();
    }

}
