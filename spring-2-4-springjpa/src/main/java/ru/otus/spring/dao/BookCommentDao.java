package ru.otus.spring.dao;

import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Optional;

/**
 * BookCommentDao
 **/
public interface BookCommentDao {

    BookComment save(BookComment bookComment);

    int deleteById(long id);

    Optional<BookComment> findById(long id);

    List<BookComment> findByBookId(long bookId);

}
