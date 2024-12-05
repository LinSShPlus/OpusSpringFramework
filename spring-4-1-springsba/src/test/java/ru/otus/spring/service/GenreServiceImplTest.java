package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.GenreRepository;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * GenreServiceImplTest
 **/
@DisplayName("Класс GenreServiceImpl")
@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    private static final long EXPECTED_GENRE_ID = 1;

    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreServiceImpl genreService;

    @DisplayName("Должен сохранить жанр")
    @Test
    void save() {
        Genre genre = createGenre();
        when(genreRepository.save(genre)).thenReturn(genre);
        assertThat(genreService.save(genre)).isEqualTo(genre);
    }

    @DisplayName("Должен удалить жанр по идентификатору")
    @Test
    void deleteById() {
        genreService.deleteById(EXPECTED_GENRE_ID);
        verify(genreRepository, times(1)).deleteById(EXPECTED_GENRE_ID);
    }

    @DisplayName("Должен получить жанр по идентификатору")
    @Test
    void getById() {
        Optional<Genre> genre = Optional.of(createGenre());
        when(genreRepository.findById(EXPECTED_GENRE_ID)).thenReturn(genre);
        assertThat(genreService.getById(EXPECTED_GENRE_ID)).isEqualTo(genre);
    }

    @DisplayName("Должен получить жанр по сокращению")
    @Test
    void getByBrief() {
        Genre genre = createGenre();
        when(genreRepository.findByBrief(genre.getBrief())).thenReturn(genre);
        assertThat(genreService.getByBrief(genre.getBrief())).isEqualTo(genre);
    }

    @DisplayName("Должен получить все жанры")
    @Test
    void getAll() {
        List<Genre> genres = List.of(createGenre());
        when(genreRepository.findAll()).thenReturn(genres);
        assertThat(genreService.getAll()).isEqualTo(genres);
    }

    private Genre createGenre() {
        return Genre
                .builder()
                .id(EXPECTED_GENRE_ID)
                .brief("Programming")
                .name("Programming")
                .build();
    }

}