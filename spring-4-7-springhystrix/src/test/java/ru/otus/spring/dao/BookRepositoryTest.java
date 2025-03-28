package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BookRepositoryTest
 **/
@DisplayName("Класс BookRepository")
@DataJpaTest
class BookRepositoryTest {

    private static final long EXPECTED_BOOK_ID = 1L;

    private static final long EXPECTED_AUTHOR_ID = 1L;
    private static final long EXPECTED_GENRE_ID = 1L;

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить книгу")
    @Test
    void insert() {
        Book book = getNewBook();
        Long id = repository.save(book).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить книгу")
    @Test
    void update() {
        Book expectedBook = getExpectedBook();
        Book actualBook = repository.save(expectedBook);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Удалить книгу по идентификатору")
    @Test
    void deleteById() {
        repository.deleteById(EXPECTED_BOOK_ID);
        em.flush();
        Book actualBook = em.find(Book.class, EXPECTED_BOOK_ID);
        assertThat(actualBook).isNull();
    }

    @DisplayName("Вернуть книгу по идентификатору")
    @Test
    void findById() {
        Book expectedBook = getExpectedBook();
        Optional<Book> optionalActualBook = repository.findById(expectedBook.getId());
        assertThat(optionalActualBook).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedBook));
    }

    @DisplayName("Вернуть книгу по сокращению")
    @Test
    void findByBrief() {
        Book expectedBook = getExpectedBook();
        Book actualBook = repository.findByBrief(expectedBook.getBrief());
        assertThat(actualBook).isNotNull().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Вернуть все книги")
    @Test
    void findAll() {
        List<Book> books = repository.findAll();
        assertThat(books).size().isEqualTo(repository.count());
    }

    @DisplayName("Вернуть количество книг")
    @Test
    void count() {
        long count = repository.count();
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