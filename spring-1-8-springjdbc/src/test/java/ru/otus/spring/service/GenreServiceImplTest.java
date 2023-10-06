package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * GenreServiceImplTest
 **/
@DisplayName("Класс GenreServiceImpl")
@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    private static final long EXPECTED_GENRE_ID = 1;

    @Mock
    private GenreDao genreDao;
    @InjectMocks
    private GenreServiceImpl genreService;

    @DisplayName("Должен добавить жанр")
    @Test
    void insert() {
        Genre genre = createGenre();
        when(genreDao.insert(genre)).thenReturn(EXPECTED_GENRE_ID);
        assertThat(genreService.insert(genre)).isEqualTo(EXPECTED_GENRE_ID);
    }

    @DisplayName("Должен обновить жанр")
    @Test
    void update() {
        Genre genre = createGenre();
        when(genreDao.update(genre)).thenReturn(1);
        assertThat(genreService.update(genre)).isEqualTo(1);
    }

    @DisplayName("Должен удалить жанр по идентификатору")
    @Test
    void deleteById() {
        when(genreDao.deleteById(EXPECTED_GENRE_ID)).thenReturn(1);
        assertThat(genreService.deleteById(EXPECTED_GENRE_ID)).isEqualTo(1);
    }

    @DisplayName("Должен получить жанр по идентификатору")
    @Test
    void getById() {
        Genre genre = createGenre();
        when(genreDao.getById(EXPECTED_GENRE_ID)).thenReturn(genre);
        assertThat(genreService.getById(EXPECTED_GENRE_ID)).isEqualTo(genre);
    }

    @DisplayName("Должен получить жанр по сокращению")
    @Test
    void getByBrief() {
        Genre genre = createGenre();
        when(genreDao.getByBrief(genre.getBrief())).thenReturn(genre);
        assertThat(genreService.getByBrief(genre.getBrief())).isEqualTo(genre);
    }

    @DisplayName("Должен получить все жанры")
    @Test
    void getAll() {
        List<Genre> genres = List.of(createGenre());
        when(genreDao.getAll()).thenReturn(genres);
        assertThat(genreService.getAll()).isEqualTo(genres);
    }

    private Genre createGenre() {
        return Genre
                .builder()
                .id(1L)
                .brief("Programming")
                .name("Programming")
                .build();
    }

}