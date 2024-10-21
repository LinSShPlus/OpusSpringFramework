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
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * BookCommentControllerTest
 **/
@DisplayName("Класс BookCommentController")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookCommentControllerTest {

    private static final long EXPECTED_BOOK_COMMENT_ID = 1L;
    private static final long EXPECTED_BOOK_ID = 1L;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private MockMvc mockMvcAccess;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("Должен добавить комментарий к книге")
    @Test
    void saveBookComment() throws Exception {
        BookComment bookComment = createBookComment(true);
        String content = objectMapper.writeValueAsString(bookComment);

        MvcResult mvcResult = mockMvc.perform(post("/api/bookcomment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        BookComment actualBookComment = objectMapper.readValue(receive, BookComment.class);
        assertThat(actualBookComment).isNotNull();
        assertThat(actualBookComment.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен изменить комментарий к книге")
    @Test
    void updateBookComment() throws Exception {
        BookComment bookComment = createBookComment(false);
        String content = objectMapper.writeValueAsString(bookComment);

        MvcResult mvcResult = mockMvc.perform(put("/api/bookcomment/{id}", bookComment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        BookComment actualBookComment = objectMapper.readValue(receive, BookComment.class);
        assertThat(actualBookComment).isNotNull();
        assertThat(actualBookComment.getId()).isNotNull().isPositive();
        assertThat(actualBookComment).usingRecursiveComparison().isEqualTo(bookComment);
    }

    @DisplayName("Должен удалить комментарий к книге по идентификатору")
    @Test
    void deleteBookCommentById() throws Exception {
        mockMvc.perform(delete("/api/bookcomment/{id}", EXPECTED_BOOK_COMMENT_ID))
                .andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Должен получить комментарий к книге по идентификатору")
    @Test
    void getBookCommentById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/bookcomment/{id}", EXPECTED_BOOK_COMMENT_ID))
                .andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        BookComment actualBookComment = objectMapper.readValue(receive, BookComment.class);
        assertThat(actualBookComment).isNotNull();
        assertThat(actualBookComment.getId()).isNotNull().isPositive();
    }

    @DisplayName("Должен получить все комментарии к книге по идентификатору книги")
    @Test
    void getAllBookComments() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/bookcomment")
                .param("bookId", Long.toString(EXPECTED_BOOK_ID))
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        List<BookComment> actualBookCommentList = objectMapper.readValue(receive, new TypeReference<>() {});
        assertThat(actualBookCommentList).isNotNull().isNotEmpty();
    }

    @DisplayName("Не должен получить комментарии к книге без авторизации")
    @Test
    void notAccessAuthor() throws Exception {
        mockMvcAccess.perform(get("/api/bookcomment"))
                .andExpect(status().isUnauthorized());
    }

    private BookComment createBookComment(boolean isNew) {
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

        Book book = Book
                .builder()
                .id(EXPECTED_BOOK_ID)
                .brief("Java_Begin")
                .title("Java for Beginners")
                .text("Text of Java for Beginners")
                .author(author)
                .genre(genre)
                .build();

        return BookComment
                .builder()
                .id(isNew ? null : EXPECTED_BOOK_COMMENT_ID)
                .book(book)
                .comment("Test comment")
                .build();
    }

}
