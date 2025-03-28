package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BookCommentRepositoryTest
 **/
@DisplayName("Класс BookCommentRepository")
@DataJpaTest
class BookCommentRepositoryTest {

    private static final long EXPECTED_BOOK_COMMENT_ID = 1L;
    private static final long EXPECTED_BOOK_ID = 1L;

    @Autowired
    private BookCommentRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить комментарий к книге")
    @Test
    void insert() {
        BookComment bookComment = getNewBookComment();
        Long id = repository.save(bookComment).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить комментарий к книге")
    @Test
    void update() {
        BookComment expectedBookComment = getExistBookComment();
        BookComment actualBookComment = repository.save(expectedBookComment);
        assertThat(actualBookComment).usingRecursiveComparison().isEqualTo(expectedBookComment);
    }

    @DisplayName("Удалить комментарий к книге по идентификатору")
    @Test
    void deleteById() {
        repository.deleteById(EXPECTED_BOOK_COMMENT_ID);
        em.flush();
        BookComment actualBookComment = em.find(BookComment.class, EXPECTED_BOOK_ID);
        assertThat(actualBookComment).isNull();
    }

    @DisplayName("Вернуть комментарий к книге по идентификатору")
    @Test
    void findById() {
        BookComment expectedBookComment = getExistBookComment();
        Optional<BookComment> optionalActualBookComment = repository.findById(expectedBookComment.getId());
        assertThat(optionalActualBookComment).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedBookComment));
    }

    @DisplayName("Вернуть все комментарии к книге по идентификатору книги")
    @Test
    void findByBookId() {
        List<BookComment> bookComments = repository.findByBookId(EXPECTED_BOOK_ID);
        assertThat(bookComments).isNotNull().isNotEmpty();
    }

    private BookComment getNewBookComment() {
        Book book = em.find(Book.class, EXPECTED_BOOK_ID);
        return BookComment
                .builder()
                .comment("comment")
                .book(book)
                .build();
    }

    private BookComment getExistBookComment() {
        return em.find(BookComment.class, EXPECTED_BOOK_COMMENT_ID);
    }

}
