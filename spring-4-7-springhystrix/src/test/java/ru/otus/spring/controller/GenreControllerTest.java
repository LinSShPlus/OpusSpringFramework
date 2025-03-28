package ru.otus.spring.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.GenreService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * GenreControllerTest
 **/
@DisplayName("Класс GenreController")
@RunWith(SpringRunner.class)
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    private static final long EXPECTED_GENRE_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Должен добавить жанр")
    @Test
    void saveGenre() throws Exception {
        Genre genre = createGenre();
        String content = objectMapper.writeValueAsString(genre);

        when(genreService.save(any())).thenReturn(genre);

        MvcResult mvcResult = mockMvc.perform(post("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Genre actualGenre = objectMapper.readValue(receive, Genre.class);
        assertThat(actualGenre).isNotNull();
        assertThat(actualGenre.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен изменить жанр")
    @Test
    void updateGenre() throws Exception {
        Genre genre = createGenre();
        String content = objectMapper.writeValueAsString(genre);

        when(genreService.getById(anyLong())).thenReturn(Optional.ofNullable(genre));
        when(genreService.save(any())).thenReturn(genre);

        MvcResult mvcResult = mockMvc.perform(put("/api/genre/{id}", EXPECTED_GENRE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Genre actualGenre = objectMapper.readValue(receive, Genre.class);
        assertThat(actualGenre).isNotNull();
        assertThat(actualGenre.getId()).isNotNull().isPositive();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(genre);
    }

    @DisplayName("Должен удалить жанр по идентификатору")
    @Test
    void deleteGenreById() throws Exception {
        mockMvc.perform(delete("/api/genre/{id}", EXPECTED_GENRE_ID))
                .andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Должен получить жанр по идентификатору")
    @Test
    void getGenreById() throws Exception {
        Genre genre = createGenre();

        when(genreService.getById(anyLong())).thenReturn(Optional.ofNullable(genre));

        MvcResult mvcResult = mockMvc.perform(get("/api/genre/{id}", EXPECTED_GENRE_ID))
                .andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Genre actualGenre = objectMapper.readValue(receive, Genre.class);
        assertThat(actualGenre).isNotNull();
        assertThat(actualGenre.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен получить жанр по сокращению")
    @Test
    void getGenreByBrief() throws Exception {
        Genre genre = createGenre();

        when(genreService.getByBrief(anyString())).thenReturn(genre);

        MvcResult mvcResult = mockMvc.perform(get("/api/genre")
                .param("brief", "Programming")
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Genre actualGenre = objectMapper.readValue(receive, Genre.class);
        assertThat(actualGenre).isNotNull();
        assertThat(actualGenre.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен получить все жанры")
    @Test
    void getAllGenres() throws Exception {
        Genre genre = createGenre();
        List<Genre> genres = Collections.singletonList(genre);

        when(genreService.getAll()).thenReturn(genres);

        MvcResult mvcResult = mockMvc.perform(get("/api/genre"))
                .andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        List<Genre> actualGenreList = objectMapper.readValue(receive, new TypeReference<List<Genre>>() {});
        assertThat(actualGenreList).isNotNull().isNotEmpty();
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
