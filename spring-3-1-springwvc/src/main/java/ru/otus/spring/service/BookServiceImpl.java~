package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.BookRepository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * BookServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public long save(Book book) {
        return bookRepository.save(book).getId();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getById(long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Book getByBrief(String brief) {
        return bookRepository.findByBrief(brief);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

}
