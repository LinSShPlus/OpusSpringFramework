package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.BookRepository;
import ru.otus.spring.domain.Book;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * BookServiceImpl
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @HystrixCommand(commandKey = "save", fallbackMethod = "buildFallbackBook")
    @Transactional
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @HystrixCommand(commandKey = "deleteById", fallbackMethod = "buildFallbackDeleteById")
    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @HystrixCommand(commandKey = "getById", fallbackMethod = "buildFallbackGetById")
    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getById(long id) {
        return bookRepository.findById(id);
    }

    @HystrixCommand(commandKey = "getByBrief", fallbackMethod = "buildFallbackGetByBrief")
    @Transactional(readOnly = true)
    @Override
    public Book getByBrief(String brief) {
        return bookRepository.findByBrief(brief);
    }

    @HystrixCommand(commandKey = "getAll", fallbackMethod = "buildFallbackBooks")
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @HystrixCommand(commandKey = "count", fallbackMethod = "buildFallbackBookCount")
    @Override
    public long count() {
        return bookRepository.count();
    }

    public Book buildFallbackBook(Book book) {
        return book;
    }

    public void buildFallbackDeleteById(long id) {
        log.info("Delete by id: " + id);
    }

    public Optional<Book> buildFallbackGetById(long id) {
        Book book = Book
                .builder()
                .id(id)
                .brief("N/A")
                .title("N/A")
                .text("N/A")
                .build();
        return Optional.ofNullable(book);
    }

    public Book buildFallbackGetByBrief(String brief) {
        return Book
                .builder()
                .id(0L)
                .brief(brief)
                .title("N/A")
                .text("N/A")
                .build();
    }

    public List<Book> buildFallbackBooks() {
        return Collections.emptyList();
    }

    public long buildFallbackBookCount() {
        return 0L;
    }

}
