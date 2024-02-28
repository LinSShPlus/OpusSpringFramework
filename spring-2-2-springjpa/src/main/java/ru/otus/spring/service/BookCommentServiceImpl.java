package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookCommentDao;
import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Optional;

/**
 * BookCommentServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentDao bookCommentDao;

    @Override
    public long save(BookComment bookComment) {
        return bookCommentDao.save(bookComment).getId();
    }

    @Override
    public int deleteById(long id) {
        return bookCommentDao.deleteById(id);
    }

    @Override
    public Optional<BookComment> getById(long id) {
        return bookCommentDao.findById(id);
    }

    @Override
    public List<BookComment> getByBookId(long bookId) {
        return bookCommentDao.findByBookId(bookId);
    }

}
