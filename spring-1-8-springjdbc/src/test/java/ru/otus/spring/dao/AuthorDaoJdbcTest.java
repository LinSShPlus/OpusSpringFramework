package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AuthorDaoJdbcTest
 **/
@DisplayName("Класс AuthorDaoJdbc")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc dao;

    @DisplayName("Добавить автора")
    @Test
    void insert() {
        Author author = createNewAuthor();
        Long id = dao.insert(author);
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить автора")
    @Test
    void update() {
        Author author = createExistAuthor();
        int rows = dao.update(author);
        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("Удалить автора по идентификатору")
    @Test
    void deleteById() {
        int rows = dao.deleteById(2L);
        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("Вернуть автора по идентификатору")
    @Test
    void getById() {
        Author expectedAuthor = createExistAuthor();
        Author actualAuthor = dao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Вернуть автора по сокращению")
    @Test
    void getByBrief() {
        Author expectedAuthor = createExistAuthor();
        Author actualAuthor = dao.getByBrief(expectedAuthor.getBrief());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Вернуть всех авторов")
    @Test
    void getAll() {
        Author author = createExistAuthor();
        List<Author> authors = dao.getAll();
        assertThat(authors).contains(author).size().isPositive();
    }

    private Author createNewAuthor() {
        return Author
                .builder()
                .brief("brief")
                .lastName("lastName")
                .firstName("firstName")
                .build();
    }

    private Author createExistAuthor() {
        return Author
                .builder()
                .id(1L)
                .brief("Ivanov I.")
                .lastName("Ivanov")
                .firstName("Ivan")
                .build();
    }

}