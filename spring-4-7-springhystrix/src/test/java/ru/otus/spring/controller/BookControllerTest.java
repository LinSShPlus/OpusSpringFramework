package ru.otus.spring.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.BookService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * BookControllerTest
 **/
@DisplayName("Класс BookController")
@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final long EXPECTED_BOOK_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Должен добавить книгу")
    @Test
    void saveBook() throws Exception {
        Book book = createBook();
        String content = objectMapper.writeValueAsString(book);

        when(bookService.save(any())).thenReturn(book);

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
        Book book = createBook();
        String content = objectMapper.writeValueAsString(book);

        when(bookService.getById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(bookService.save(any())).thenReturn(book);

        MvcResult mvcResult = mockMvc.perform(put("/api/book/{id}", EXPECTED_BOOK_ID)
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
        Book book = createBook();

        when(bookService.getById(anyLong())).thenReturn(Optional.ofNullable(book));

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
        Book book = createBook();

        when(bookService.getByBrief(anyString())).thenReturn(book);

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
        Book book = createBook();
        List<Book> books = Collections.singletonList(book);

        when(bookService.getAll()).thenReturn(books);

        MvcResult mvcResult = mockMvc.perform(get("/api/book"))
                .andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        List<Book> actualBookList = objectMapper.readValue(receive, new TypeReference<List<Book>>() {});
        assertThat(actualBookList).isNotNull().isNotEmpty();
    }

    private Book createBook() {
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
                .id(EXPECTED_BOOK_ID)
                .brief("Java_Begin")
                .title("Java for Beginners")
                .text("Text of Java for Beginners")
                .author(author)
                .genre(genre)
                .build();
    }

}
