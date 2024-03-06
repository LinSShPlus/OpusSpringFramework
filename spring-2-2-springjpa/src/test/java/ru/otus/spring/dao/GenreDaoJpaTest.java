package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GenreDaoJpaTest
 **/
@DisplayName("Класс GenreDaoJpa")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    private static final long EXPECTED_GENRE_ID = 1L;

    @Autowired
    private GenreDaoJpa dao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить жанр")
    @Test
    void insert() {
        Genre genre = getNewGenre();
        Long id = dao.save(genre).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить жанр")
    @Test
    void update() {
        Genre expectedGenre = getExistGenre();
        Genre actualGenre = dao.save(expectedGenre);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Удалить жанр по идентификатору")
    @Test
    void deleteById() {
        int rows = dao.deleteById(EXPECTED_GENRE_ID);
        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("Вернуть жанр по идентификатору")
    @Test
    void findById() {
        Genre expectedGenre = getExistGenre();
        Optional<Genre> optionalActualGenre = dao.findById(expectedGenre.getId());
        assertThat(optionalActualGenre).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedGenre));
    }

    @DisplayName("Вернуть жанр по сокращению")
    @Test
    void findByBrief() {
        Genre expectedGenre = getExistGenre();
        Genre actualGenre = dao.findByBrief(expectedGenre.getBrief());
        assertThat(actualGenre).isNotNull().usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Вернуть все жанры")
    @Test
    void findAll() {
        List<Genre> genres = dao.findAll();
        assertThat(genres).size().isPositive();
    }

    private Genre getNewGenre() {
        return Genre
                .builder()
                .brief("brief")
                .name("name")
                .build();
    }

    private Genre getExistGenre() {
        return em.find(Genre.class, EXPECTED_GENRE_ID);
    }

}