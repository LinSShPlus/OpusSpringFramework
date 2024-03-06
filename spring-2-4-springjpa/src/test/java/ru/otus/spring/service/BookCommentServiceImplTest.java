package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.BookCommentRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * BookCommentServiceImplTest
 **/
@DisplayName("Класс BookCommentServiceImpl")
@ExtendWith(MockitoExtension.class)
class BookCommentServiceImplTest {

    private static final long EXPECTED_BOOK_COMMENT_ID = 1L;
    private static final long EXPECTED_BOOK_ID = 1L;
    private static final long EXPECTED_AUTHOR_ID = 1L;
    private static final long EXPECTED_GENRE_ID = 1L;

    @Mock
    private BookCommentRepository bookCommentRepository;
    @InjectMocks
    private BookCommentServiceImpl bookCommentService;

    @DisplayName("Должен добавить комментарий к книге")
    @Test
    void insert() {
        BookComment bookComment = createBookComment();
        when(bookCommentRepository.save(bookComment)).thenReturn(bookComment);
        assertThat(bookCommentService.save(bookComment)).isEqualTo(bookComment.getId());
    }

    @DisplayName("Должен обновить комментарий к книге")
    @Test
    void update() {
        BookComment bookComment = createBookComment();
        when(bookCommentRepository.save(bookComment)).thenReturn(bookComment);
        assertThat(bookCommentService.save(bookComment)).isEqualTo(bookComment.getId());
    }

    @DisplayName("Должен удалить комментарий к книге по идентификатору")
    @Test
    void deleteById() {
        bookCommentService.deleteById(EXPECTED_BOOK_COMMENT_ID);
        verify(bookCommentRepository, times(1)).deleteById(EXPECTED_BOOK_COMMENT_ID);
    }

    @DisplayName("Должен получить комментарий к книге по идентификатору")
    @Test
    void getById() {
        Optional<BookComment> bookComment = Optional.of(createBookComment());
        when(bookCommentRepository.findById(EXPECTED_BOOK_ID)).thenReturn(bookComment);
        assertThat(bookCommentService.getById(EXPECTED_BOOK_ID)).isEqualTo(bookComment);
    }

    @DisplayName("Должен получить все комментарии к книге по идентификатору книги")
    @Test
    void getByBookId() {
        Book book = createBook();
        List<BookComment> bookComments = List.of(
                BookComment
                        .builder()
                        .id(1L)
                        .comment("Comment 1")
                        .book(book)
                        .build(),
                BookComment
                        .builder()
                        .id(2L)
                        .comment("Comment 2")
                        .book(book)
                        .build()
        );
        when(bookCommentRepository.findByBookId(anyLong())).thenReturn(bookComments);
        assertThat(bookCommentService.getByBookId(book.getId())).usingRecursiveComparison().isEqualTo(bookComments);
    }

    private BookComment createBookComment() {
        Book book = createBook();
        return BookComment
                .builder()
                .id(EXPECTED_BOOK_COMMENT_ID)
                .comment("I am going to say (write) a few words about a book (story) I have recently read")
                .book(book)
                .build();
    }

    private Book createBook() {
        Author author = Author
                .builder()
                .id(EXPECTED_AUTHOR_ID)
                .brief("testBrief")
                .lastName("testLastName")
                .firstName("testFirstName")
                .build();
        Genre genre = Genre
                .builder()
                .id(EXPECTED_GENRE_ID)
                .brief("testBrief")
                .name("testName")
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
