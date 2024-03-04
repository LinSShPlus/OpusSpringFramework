package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * BookServiceImplTest
 **/
@DisplayName("Класс BookServiceImpl")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private static final long EXPECTED_BOOK_ID = 1L;
    private static final long EXPECTED_AUTHOR_ID = 1L;
    private static final long EXPECTED_GENRE_ID = 1L;
    private static final long EXPECTED_BOOKS_COUNT = 5;

    @Mock
    private BookDao bookDao;
    @InjectMocks
    private BookServiceImpl bookService;

    @DisplayName("Должен добавить книгу")
    @Test
    void insert() {
        Book book = createBook();
        when(bookDao.save(book)).thenReturn(book);
        assertThat(bookService.save(book)).isEqualTo(book.getId());
    }

    @DisplayName("Должен обновить книгу")
    @Test
    void update() {
        Book book = createBook();
        when(bookDao.save(book)).thenReturn(book);
        assertThat(bookService.save(book)).isEqualTo(book.getId());
    }

    @DisplayName("Должен удалить книгу по идентификатору")
    @Test
    void deleteById() {
        final int rows = 1;
        when(bookDao.deleteById(EXPECTED_BOOK_ID)).thenReturn(rows);
        assertThat(bookService.deleteById(EXPECTED_BOOK_ID)).isEqualTo(rows);
    }

    @DisplayName("Должен получить книгу по идентификатору")
    @Test
    void getById() {
        Optional<Book> book = Optional.of(createBook());
        when(bookDao.findById(EXPECTED_BOOK_ID)).thenReturn(book);
        assertThat(bookService.getById(EXPECTED_BOOK_ID)).isEqualTo(book);
    }

    @DisplayName("Должен получить книгу по сокращению")
    @Test
    void getByBrief() {
        Book book = createBook();
        when(bookDao.findByBrief(book.getBrief())).thenReturn(book);
        assertThat(bookService.getByBrief(book.getBrief())).isEqualTo(book);
    }

    @DisplayName("Должен получить все книги")
    @Test
    void getAll() {
        List<Book> books = List.of(createBook());
        when(bookDao.findAll()).thenReturn(books);
        assertThat(bookService.getAll()).isEqualTo(books);
    }

    @DisplayName("Должен получить количество книг")
    @Test
    void count() {
        when(bookDao.count()).thenReturn(EXPECTED_BOOKS_COUNT);
        assertThat(bookService.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
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