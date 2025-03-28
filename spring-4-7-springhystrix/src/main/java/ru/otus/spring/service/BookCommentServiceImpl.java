package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.BookCommentRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * BookCommentServiceImpl
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    @HystrixCommand(commandKey = "save", fallbackMethod = "buildFallbackBookComment")
    @Transactional
    @Override
    public BookComment save(BookComment bookComment) {
        return bookCommentRepository.save(bookComment);
    }

    @HystrixCommand(commandKey = "deleteById", fallbackMethod = "buildFallbackDeleteById")
    @Transactional
    @Override
    public void deleteById(long id) {
        bookCommentRepository.deleteById(id);
    }

    @HystrixCommand(commandKey = "getById", fallbackMethod = "buildFallbackGetById")
    @Transactional(readOnly = true)
    @Override
    public Optional<BookComment> getById(long id) {
        return bookCommentRepository.findById(id);
    }

    @HystrixCommand(commandKey = "getByBookId", fallbackMethod = "buildFallbackGetByBookId")
    @Transactional(readOnly = true)
    @Override
    public List<BookComment> getByBookId(long bookId) {
        return bookCommentRepository.findByBookId(bookId);
    }

    public BookComment buildFallbackBookComment(BookComment bookComment) {
        return bookComment;
    }

    public void buildFallbackDeleteById(long id) {
        log.info("Delete by id: " + id);
    }

    public Optional<BookComment> buildFallbackGetById(long id) {
        BookComment bookComment = BookComment
                .builder()
                .id(id)
                .comment("N/A")
                .build();
        return Optional.ofNullable(bookComment);
    }

    public List<BookComment> buildFallbackGetByBookId(long bookId) {
        Book book = Book
                .builder()
                .id(bookId)
                .brief("N/A")
                .title("N/A")
                .text("N/A")
                .build();
        BookComment bookComment = BookComment
                .builder()
                .id(0L)
                .comment("N/A")
                .book(book)
                .build();
        List<BookComment> bookComments = new ArrayList<>();
        bookComments.add(bookComment);
        return bookComments;
    }

}
