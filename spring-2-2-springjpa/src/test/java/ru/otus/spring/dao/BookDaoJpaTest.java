package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BookDaoJpaTest
 **/
@DisplayName("Класс BookDaoJpa")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
@Import(BookDaoJpa.class)
class BookDaoJpaTest {

    private static final long EXPECTED_BOOK_ID = 1L;

    private static final long EXPECTED_AUTHOR_ID = 1L;
    private static final long EXPECTED_GENRE_ID = 1L;

    @Autowired
    private BookDaoJpa dao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить книгу")
    @Test
    void insert() {
        Book book = getNewBook();
        Long id = dao.save(book).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить книгу")
    @Test
    void update() {
        Book expectedBook = getExpectedBook();
        Book actualBook = dao.save(expectedBook);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Удалить книгу по идентификатору")
    @Test
    void deleteById() {
        int rows = dao.deleteById(EXPECTED_BOOK_ID);
        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("Вернуть книгу по идентификатору")
    @Test
    void findById() {
        Book expectedBook = getExpectedBook();
        Optional<Book> optionalActualBook = dao.findById(expectedBook.getId());
        assertThat(optionalActualBook).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedBook));
    }

    @DisplayName("Вернуть книгу по сокращению")
    @Test
    void findByBrief() {
        Book expectedBook = getExpectedBook();
        Book actualBook = dao.findByBrief(expectedBook.getBrief());
        assertThat(actualBook).isNotNull().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Вернуть все книги")
    @Test
    void findAll() {
        List<Book> books = dao.findAll();
        assertThat(books).size().isEqualTo(dao.count());
    }

    @DisplayName("Вернуть количество книг")
    @Test
    void count() {
        long count = dao.count();
        assertThat(count).isPositive();
    }

    private Book getNewBook() {
        Author author = em.find(Author.class, EXPECTED_AUTHOR_ID);
        Genre genre = em.find(Genre.class, EXPECTED_GENRE_ID);
        return Book
                .builder()
                .brief("brief")
                .title("title")
                .text("text")
                .author(author)
                .genre(genre)
                .build();
    }

    private Book getExpectedBook() {
        return em.find(Book.class, EXPECTED_BOOK_ID);
    }

}