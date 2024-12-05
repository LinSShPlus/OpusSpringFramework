package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AuthorRepositoryTest
 **/
@DisplayName("Класс AuthorRepository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
class AuthorRepositoryTest {

    private static final long EXPECTED_AUTHOR_ID = 1L;

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить автора")
    @Test
    void insert() {
        Author author = getNewAuthor();
        Long id = repository.save(author).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить автора")
    @Test
    void update() {
        Author expectedAuthor = getExistAuthor();
        Author actualAuthor = repository.save(expectedAuthor);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Удалить автора по идентификатору")
    @Test
    void deleteById() {
        repository.deleteById(EXPECTED_AUTHOR_ID);
        em.flush();
        Author actualAuthor = em.find(Author.class, EXPECTED_AUTHOR_ID);
        assertThat(actualAuthor).isNull();
    }

    @DisplayName("Вернуть автора по идентификатору")
    @Test
    void findById() {
        Author expectedAuthor = getExistAuthor();
        Optional<Author> optionalActualAuthor = repository.findById(expectedAuthor.getId());
        assertThat(optionalActualAuthor).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedAuthor));
    }

    @DisplayName("Вернуть автора по сокращению")
    @Test
    void findByBrief() {
        Author expectedAuthor = getExistAuthor();
        Author actualAuthor = repository.findByBrief(expectedAuthor.getBrief());
        assertThat(actualAuthor).isNotNull().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Вернуть всех авторов")
    @Test
    void findAll() {
        List<Author> authors = repository.findAll();
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