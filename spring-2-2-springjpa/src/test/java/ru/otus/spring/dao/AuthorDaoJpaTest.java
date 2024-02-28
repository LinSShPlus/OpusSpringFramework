package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AuthorDaoJpaTest
 **/
@DisplayName("Класс AuthorDaoJpa")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {

    private static final long EXPECTED_AUTHOR_ID = 1L;

    @Autowired
    private AuthorDaoJpa dao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить автора")
    @Test
    void insert() {
        Author author = getNewAuthor();
        Long id = dao.save(author).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить автора")
    @Test
    void update() {
        Author expectedAuthor = getExistAuthor();
        Author actualAuthor = dao.save(expectedAuthor);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Удалить автора по идентификатору")
    @Test
    void deleteById() {
        int rows = dao.deleteById(EXPECTED_AUTHOR_ID);
        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("Вернуть автора по идентификатору")
    @Test
    void findById() {
        Author expectedAuthor = getExistAuthor();
        Optional<Author> optionalActualAuthor = dao.findById(expectedAuthor.getId());
        assertThat(optionalActualAuthor).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedAuthor));
    }

    @DisplayName("Вернуть автора по сокращению")
    @Test
    void findByBrief() {
        Author expectedAuthor = getExistAuthor();
        Author actualAuthor = dao.findByBrief(expectedAuthor.getBrief());
        assertThat(actualAuthor).isNotNull().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Вернуть всех авторов")
    @Test
    void findAll() {
        List<Author> authors = dao.findAll();
        assertThat(authors).size().isPositive();
    }

    private Author getNewAuthor() {
        return Author
                .builder()
                .brief("brief")
                .lastName("lastName")
                .firstName("firstName")
                .build();
    }

    private Author getExistAuthor() {
        return em.find(Author.class, EXPECTED_AUTHOR_ID);
    }

}