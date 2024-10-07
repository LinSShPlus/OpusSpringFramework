package ru.otus.spring.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * GenreControllerTest
 **/
@DisplayName("Класс GenreControllerTest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GenreControllerTest {

    private static final long EXPECTED_GENRE_ID = 1L;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GenreService genreService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("Должен добавить жанр")
    @Test
    void saveGenre() throws Exception {
        Genre genre = createGenre(true);
        String content = objectMapper.writeValueAsString(genre);

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
        Genre genre = createGenre(false);
        String content = objectMapper.writeValueAsString(genre);

        MvcResult mvcResult = mockMvc.perform(put("/api/genre/{id}", genre.getId())
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
        final long id = EXPECTED_GENRE_ID;
        mockMvc.perform(delete("/api/genre/{id}", id))
                .andExpect(status().isOk()).andReturn();

        Optional<Genre> actualGenre = genreService.getById(id);
        assertThat(actualGenre).isEmpty();
    }

    @DisplayName("Должен получить жанр по идентификатору")
    @Test
    void getGenreById() throws Exception {
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
        MvcResult mvcResult = mockMvc.perform(get("/api/genre"))
                .andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        List<Genre> actualGenreList = objectMapper.readValue(receive, new TypeReference<>() {});
        assertThat(actualGenreList).isNotNull().isNotEmpty();
    }

    private Genre createGenre(boolean isNew) {
        return Genre
                .builder()
                .id(isNew ? null : EXPECTED_GENRE_ID)
                .brief("brief")
                .name("name")
                .build();
    }

}
