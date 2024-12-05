package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.BookCommentRepository;
import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Optional;

/**
 * BookCommentServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    @Transactional
    @Override
    public BookComment save(BookComment bookComment) {
        return bookCommentRepository.save(bookComment);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookCommentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<BookComment> getById(long id) {
        return bookCommentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> getByBookId(long bookId) {
        return bookCommentRepository.findByBookId(bookId);
    }

}
