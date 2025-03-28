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
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.BookCommentService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * BookCommentControllerTest
 **/
@DisplayName("Класс BookCommentController")
@RunWith(SpringRunner.class)
@WebMvcTest(BookCommentController.class)
class BookCommentControllerTest {

    private static final long EXPECTED_BOOK_COMMENT_ID = 1L;
    private static final long EXPECTED_BOOK_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookCommentService bookCommentService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Должен добавить комментарий к книге")
    @Test
    void saveBookComment() throws Exception {
        BookComment bookComment = createBookComment();
        String content = objectMapper.writeValueAsString(bookComment);

        when(bookCommentService.save(any())).thenReturn(bookComment);

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
        BookComment bookComment = createBookComment();
        String content = objectMapper.writeValueAsString(bookComment);

        when(bookCommentService.getById(anyLong())).thenReturn(Optional.ofNullable(bookComment));
        when(bookCommentService.save(any())).thenReturn(bookComment);

        MvcResult mvcResult = mockMvc.perform(put("/api/bookcomment/{id}", EXPECTED_BOOK_COMMENT_ID)
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
        BookComment bookComment = createBookComment();

        when(bookCommentService.getById(anyLong())).thenReturn(Optional.ofNullable(bookComment));

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
        BookComment bookComment = createBookComment();
        List<BookComment> bookComments = Collections.singletonList(bookComment);

        when(bookCommentService.getByBookId(anyLong())).thenReturn(bookComments);

        MvcResult mvcResult = mockMvc.perform(get("/api/bookcomment")
                .param("bookId", Long.toString(EXPECTED_BOOK_ID))
        ).andExpect(status().isOk()).andReturn();

        String receive = mvcResult.getResponse().getContentAsString();
        List<BookComment> actualBookCommentList = objectMapper.readValue(receive, new TypeReference<List<BookComment>>() {});
        assertThat(actualBookCommentList).isNotNull().isNotEmpty();
    }

    private BookComment createBookComment() {
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
                .id(EXPECTED_BOOK_COMMENT_ID)
                .book(book)
                .comment("Test comment")
                .build();
    }

}
