package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GenreRepositoryTest
 **/
@DisplayName("Класс GenreRepository")
@DataJpaTest
class GenreRepositoryTest {

    private static final long EXPECTED_GENRE_ID = 1L;

    @Autowired
    private GenreRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить жанр")
    @Test
    void insert() {
        Genre genre = getNewGenre();
        Long id = repository.save(genre).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить жанр")
    @Test
    void update() {
        Genre expectedGenre = getExistGenre();
        Genre actualGenre = repository.save(expectedGenre);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Удалить жанр по идентификатору")
    @Test
    void deleteById() {
        repository.deleteById(EXPECTED_GENRE_ID);
        em.flush();
        Genre actualGenre = em.find(Genre.class, EXPECTED_GENRE_ID);
        assertThat(actualGenre).isNull();
    }

    @DisplayName("Вернуть жанр по идентификатору")
    @Test
    void findById() {
        Genre expectedGenre = getExistGenre();
        Optional<Genre> optionalActualGenre = repository.findById(expectedGenre.getId());
        assertThat(optionalActualGenre).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedGenre));
    }

    @DisplayName("Вернуть жанр по сокращению")
    @Test
    void findByBrief() {
        Genre expectedGenre = getExistGenre();
        Genre actualGenre = repository.findByBrief(expectedGenre.getBrief());
        assertThat(actualGenre).isNotNull().usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Вернуть все жанры")
    @Test
    void findAll() {
        List<Genre> genres = repository.findAll();
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