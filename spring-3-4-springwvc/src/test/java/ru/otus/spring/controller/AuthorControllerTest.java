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
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * AuthorControllerTest
 **/
@DisplayName("Класс AuthorControllerTest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthorControllerTest {

    private static final long EXPECTED_AUTHOR_ID = 1L;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorService authorService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("Должен добавить автора")
    @Test
    void saveAuthor() throws Exception {
        Author author = createAuthor(true);
        String content = objectMapper.writeValueAsString(author);

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
        Author author = createAuthor(false);
        String content = objectMapper.writeValueAsString(author);

        MvcResult mvcResult = mockMvc.perform(put("/api/author/{id}", author.getId())
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
        final long id = EXPECTED_AUTHOR_ID;
        mockMvc.perform(delete("/api/author/{id}", id))
                .andExpect(status().isOk()).andReturn();

        Optional<Author> actualAuthor = authorService.getById(id);
        assertThat(actualAuthor).isEmpty();
    }

    @DisplayName("Должен получить автора по идентификатору")
    @Test
    void getAuthorById() throws Exception {
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
        MvcResult mvcResult = mockMvc.perform(get("/api/author"))
                .andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        List<Author> actualAuthorList = objectMapper.readValue(receive, new TypeReference<>() {});
        assertThat(actualAuthorList).isNotNull().isNotEmpty();
    }

    private Author createAuthor(boolean isNew) {
        return Author
                .builder()
                .id(isNew ? null : EXPECTED_AUTHOR_ID)
                .brief("Sidorov P.")
                .lastName("Sidorov")
                .firstName("Petya")
                .build();
    }

}
