package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GenreDaoJdbcTest
 **/
@DisplayName("Класс GenreDaoJdbc")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc dao;

    @DisplayName("Добавить жанр")
    @Test
    void insert() {
        Genre genre = createNewGenre();
        Long id = dao.insert(genre);
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить жанр")
    @Test
    void update() {
        Genre genre = createExistGenre();
        int rows = dao.update(genre);
        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("Удалить жанр по идентификатору")
    @Test
    void deleteById() {
        int rows = dao.deleteById(2L);
        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("Вернуть жанр по идентификатору")
    @Test
    void getById() {
        Genre expectedGenre = createExistGenre();
        Genre actualGenre = dao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Вернуть жанр по сокращению")
    @Test
    void getByBrief() {
        Genre expectedGenre = createExistGenre();
        Genre actualGenre = dao.getByBrief(expectedGenre.getBrief());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Вернуть все жанры")
    @Test
    void getAll() {
        Genre genre = createExistGenre();
        List<Genre> genres = dao.getAll();
        assertThat(genres).contains(genre).size().isPositive();
    }

    private Genre createNewGenre() {
        return Genre
                .builder()
                .brief("brief")
                .name("name")
                .build();
    }

    private Genre createExistGenre() {
        return Genre
                .builder()
                .id(1L)
                .brief("Programming")
                .name("Programming")
                .build();
    }

}