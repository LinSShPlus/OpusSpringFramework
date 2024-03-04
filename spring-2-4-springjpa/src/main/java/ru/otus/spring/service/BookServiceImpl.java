package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * BookServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public long save(Book book) {
        return bookDao.save(book).getId();
    }

    @Override
    public int deleteById(long id) {
        return bookDao.deleteById(id);
    }

    @Override
    public Optional<Book> getById(long id) {
        return bookDao.findById(id);
    }

    @Override
    public Book getByBrief(String brief) {
        return bookDao.findByBrief(brief);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.findAll();
    }

    @Override
    public long count() {
        return bookDao.count();
    }

}
