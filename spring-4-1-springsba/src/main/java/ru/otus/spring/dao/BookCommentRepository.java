package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.BookComment;

import java.util.List;

/**
 * BookCommentRepository
 **/
@Repository
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {

    List<BookComment> findByBookId(long bookId);

}
