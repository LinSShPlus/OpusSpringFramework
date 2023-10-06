package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * BookServiceImplTest
 **/
@DisplayName("Класс BookServiceImpl")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private static final long EXPECTED_BOOK_ID = 1;
    private static final int EXPECTED_BOOKS_COUNT = 1;

    @Mock
    private BookDao bookDao;
    @InjectMocks
    private BookServiceImpl bookService;

    @DisplayName("Должен добавить книгу")
    @Test
    void insert() {
        Book book = createBook();
        when(bookDao.insert(book)).thenReturn(EXPECTED_BOOK_ID);
        assertThat(bookService.insert(book)).isEqualTo(EXPECTED_BOOK_ID);
    }

    @DisplayName("Должен обновить книгу")
    @Test
    void update() {
        Book book = createBook();
        when(bookDao.update(book)).thenReturn(1);
        assertThat(bookService.update(book)).isEqualTo(1);
    }

    @DisplayName("Должен удалить книгу по идентификатору")
    @Test
    void deleteById() {
        when(bookDao.deleteById(EXPECTED_BOOK_ID)).thenReturn(1);
        assertThat(bookService.deleteById(EXPECTED_BOOK_ID)).isEqualTo(1);
    }

    @DisplayName("Должен получить книгу по идентификатору")
    @Test
    void getById() {
        Book book = createBook();
        when(bookDao.getById(EXPECTED_BOOK_ID)).thenReturn(book);
        assertThat(bookService.getById(EXPECTED_BOOK_ID)).isEqualTo(book);
    }

    @DisplayName("Должен получить книгу по сокращению")
    @Test
    void getByBrief() {
        Book book = createBook();
        when(bookDao.getByBrief(book.getBrief())).thenReturn(book);
        assertThat(bookService.getByBrief(book.getBrief())).isEqualTo(book);
    }

    @DisplayName("Должен получить все книги")
    @Test
    void getAll() {
        List<Book> books = List.of(createBook());
        when(bookDao.getAll()).thenReturn(books);
        assertThat(bookService.getAll()).isEqualTo(books);
    }

    @DisplayName("Должен получить количество книг")
    @Test
    void count() {
        when(bookDao.count()).thenReturn(EXPECTED_BOOKS_COUNT);
        assertThat(bookService.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    private Book createBook() {
        return Book
                .builder()
                .id(1L)
                .brief("Java_Begin")
                .title("Java for Beginners")
                .text("Text of Java for Beginners")
                .authorId(1L)
                .genreId(1L)
                .build();
    }

}