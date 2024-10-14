package ru.otus.spring.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * BookControllerTest
 **/
@DisplayName("Класс BookController")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookControllerTest {

    private static final long EXPECTED_BOOK_ID = 1L;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("Должен добавить книгу")
    @Test
    void saveBook() throws Exception {
        Book book = createBook(true);
        String content = objectMapper.writeValueAsString(book);

        MvcResult mvcResult = mockMvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Book actualBook = objectMapper.readValue(receive, Book.class);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен изменить книгу")
    @Test
    void updateBook() throws Exception {
        Book book = createBook(false);
        String content = objectMapper.writeValueAsString(book);

        MvcResult mvcResult = mockMvc.perform(put("/api/book/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Book actualBook = objectMapper.readValue(receive, Book.class);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isNotNull().isPositive();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("Должен удалить книгу по идентификатору")
    @Test
    void deleteBookById() throws Exception {
        mockMvc.perform(delete("/api/book/{id}", EXPECTED_BOOK_ID))
                .andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Должен получить книгу по идентификатору")
    @Test
    void getBookById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/book/{id}", EXPECTED_BOOK_ID))
                .andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Book actualBook = objectMapper.readValue(receive, Book.class);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен получить книгу по сокращению")
    @Test
    void getBookByBrief() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/book")
                .param("brief", "Java_Begin")
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        Book actualBook = objectMapper.readValue(receive, Book.class);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен получить все книги")
    @Test
    void getAllBooks() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/book"))
                .andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        List<Book> actualBookList = objectMapper.readValue(receive, new TypeReference<>() {});
        assertThat(actualBookList).isNotNull().isNotEmpty();
    }

    private Book createBook(boolean isNew) {
        Author author = Author
                .builder()
                .id(1L)
                .brief("Ivanov I.")
                .lastName("Ivanov")
                .firstName("Ivan")
                .build();

        Genre genre = Genre
                .builder()
                .id(1L)
                .brief("Programming")
                .name("Programming")
                .build();

        return Book
                .builder()
                .id(isNew ? null : EXPECTED_BOOK_ID)
                .brief("brief")
                .title("title")
                .text("text")
                .author(author)
                .genre(genre)
                .build();
    }

}
