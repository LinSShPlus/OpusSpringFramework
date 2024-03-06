package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BookCommentDaoJpaTest
 **/
@DisplayName("Класс BookCommentDaoJpa")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
@Import(BookCommentDaoJpa.class)
class BookCommentDaoJpaTest {

    private static final long EXPECTED_BOOK_COMMENT_ID = 1L;
    private static final long EXPECTED_BOOK_ID = 1L;

    @Autowired
    private BookCommentDaoJpa dao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить комментарий к книге")
    @Test
    void insert() {
        BookComment bookComment = getNewBookComment();
        Long id = dao.save(bookComment).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить комментарий к книге")
    @Test
    void update() {
        BookComment expectedBookComment = getExistBookComment();
        BookComment actualBookComment = dao.save(expectedBookComment);
        assertThat(actualBookComment).usingRecursiveComparison().isEqualTo(expectedBookComment);
    }

    @DisplayName("Удалить комментарий к книге по идентификатору")
    @Test
    void deleteById() {
        int rows = dao.deleteById(EXPECTED_BOOK_COMMENT_ID);
        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("Вернуть комментарий к книге по идентификатору")
    @Test
    void findById() {
        BookComment expectedBookComment = getExistBookComment();
        Optional<BookComment> optionalActualBookComment = dao.findById(expectedBookComment.getId());
        assertThat(optionalActualBookComment).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedBookComment));
    }

    @DisplayName("Вернуть все комментарии к книге по идентификатору книги")
    @Test
    void findByBookId() {
        List<BookComment> bookComments = dao.findByBookId(EXPECTED_BOOK_ID);
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
