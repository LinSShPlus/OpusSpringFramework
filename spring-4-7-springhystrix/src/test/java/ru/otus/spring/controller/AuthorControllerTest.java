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
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.AuthorService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * AuthorControllerTest
 **/
@DisplayName("Класс AuthorController")
@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    private static final long EXPECTED_AUTHOR_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Должен добавить автора")
    @Test
    void saveAuthor() throws Exception {
        Author author = createAuthor();
        String content = objectMapper.writeValueAsString(author);

        when(authorService.save(any())).thenReturn(author);

        MvcResult mvcResult = mockMvc.perform(post("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Author actualAuthor = objectMapper.readValue(receive, Author.class);
        assertThat(actualAuthor).isNotNull();
        assertThat(actualAuthor.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен изменить автора")
    @Test
    void updateAuthor() throws Exception {
        Author author = createAuthor();
        String content = objectMapper.writeValueAsString(author);

        when(authorService.getById(anyLong())).thenReturn(Optional.ofNullable(author));
        when(authorService.save(any())).thenReturn(author);

        MvcResult mvcResult = mockMvc.perform(put("/api/author/{id}", EXPECTED_AUTHOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Author actualAuthor = objectMapper.readValue(receive, Author.class);
        assertThat(actualAuthor).isNotNull();
        assertThat(actualAuthor.getId()).isNotNull().isPositive();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(author);
    }

    @DisplayName("Должен удалить автора по идентификатору")
    @Test
    void deleteAuthorById() throws Exception {
        mockMvc.perform(delete("/api/author/{id}", EXPECTED_AUTHOR_ID))
                .andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Должен получить автора по идентификатору")
    @Test
    void getAuthorById() throws Exception {
        Author author = createAuthor();

        when(authorService.getById(anyLong())).thenReturn(Optional.ofNullable(author));

        MvcResult mvcResult = mockMvc.perform(get("/api/author/{id}", EXPECTED_AUTHOR_ID))
                .andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Author actualAuthor = objectMapper.readValue(receive, Author.class);
        assertThat(actualAuthor).isNotNull();
        assertThat(actualAuthor.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен получить автора по сокращению")
    @Test
    void getAuthorByBrief() throws Exception {
        Author author = createAuthor();

        when(authorService.getByBrief(anyString())).thenReturn(author);

        MvcResult mvcResult = mockMvc.perform(get("/api/author")
                .param("brief", "Ivanov I.")
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Author actualAuthor = objectMapper.readValue(receive, Author.class);
        assertThat(actualAuthor).isNotNull();
        assertThat(actualAuthor.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен получить всех авторов")
    @Test
    void getAllAuthors() throws Exception {
        Author author = createAuthor();
        List<Author> authors = Collections.singletonList(author);

        when(authorService.getAll()).thenReturn(authors);

        MvcResult mvcResult = mockMvc.perform(get("/api/author"))
                .andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        List<Author> actualAuthorList = objectMapper.readValue(receive, new TypeReference<List<Author>>() {});
        assertThat(actualAuthorList).isNotNull().isNotEmpty();
    }

    private Author createAuthor() {
        return Author
                .builder()
                .id(EXPECTED_AUTHOR_ID)
                .brief("Sidorov P.")
                .lastName("Sidorov")
                .firstName("Petya")
                .build();
    }

}
