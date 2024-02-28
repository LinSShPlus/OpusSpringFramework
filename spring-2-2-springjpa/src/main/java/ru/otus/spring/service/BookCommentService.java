package ru.otus.spring.service;

import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Optional;

/**
 * BookCommentService
 **/
public interface BookCommentService {

    long save(BookComment bookComment);

    int deleteById(long id);

    Optional<BookComment> getById(long id);

    List<BookComment> getByBookId(long bookId);

}
