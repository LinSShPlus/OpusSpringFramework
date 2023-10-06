package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BookDaoJdbcTest
 **/
@DisplayName("Класс BookDaoJdbc")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc dao;

    @DisplayName("Добавить книгу")
    @Test
    void insert() {
        Book book = createNewBook();
        Long id = dao.insert(book);
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить книгу")
    @Test
    void update() {
        Book book = createExistBook();
        int rows = dao.update(book);
        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("Удалить книгу по идентификатору")
    @Test
    void deleteById() {
        int rows = dao.deleteById(1L);
        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("Вернуть книгу по идентификатору")
    @Test
    void getById() {
        Book expectedBook = createExistBook();
        Book actualBook = dao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Вернуть книгу по сокращению")
    @Test
    void getByBrief() {
        Book expectedBook = createExistBook();
        Book actualBook = dao.getByBrief(expectedBook.getBrief());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Вернуть все книги")
    @Test
    void getAll() {
        Book book = createExistBook();
        List<Book> books = dao.getAll();
        assertThat(books).contains(book).size().isEqualTo(dao.count());
    }

    @DisplayName("Вернуть количество книг")
    @Test
    void count() {
        int count = dao.count();
        assertThat(count).isPositive();
    }

    private Book createNewBook() {
        return Book
                .builder()
                .brief("brief")
                .title("title")
                .text("text")
                .authorId(1L)
                .genreId(1L)
                .build();
    }

    private Book createExistBook() {
        return Book
                .builder()
                .id(1L)
                .brief("Java_Begin")
                .title("Java for Beginners")
                .text("Text of Java for Beginners")
                .authorId(1L)
                .genreId(1L)
                .build();
    }

}