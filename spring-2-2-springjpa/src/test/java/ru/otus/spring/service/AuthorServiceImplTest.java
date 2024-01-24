package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * AuthorServiceImplTest
 **/
@DisplayName("Класс AuthorServiceImpl")
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    private static final long EXPECTED_AUTHOR_ID = 1;

    @Mock
    private AuthorDao authorDao;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @DisplayName("Должен добавить автора")
    @Test
    void insert() {
        Author author = createAuthor();
        when(authorDao.insert(author)).thenReturn(EXPECTED_AUTHOR_ID);
        assertThat(authorService.insert(author)).isEqualTo(EXPECTED_AUTHOR_ID);
    }

    @DisplayName("Должен обновить автора")
    @Test
    void update() {
        Author author = createAuthor();
        when(authorDao.update(author)).thenReturn(1);
        assertThat(authorService.update(author)).isEqualTo(1);
    }

    @DisplayName("Должен удалить автора по идентификатору")
    @Test
    void deleteById() {
        when(authorDao.deleteById(EXPECTED_AUTHOR_ID)).thenReturn(1);
        assertThat(authorService.deleteById(EXPECTED_AUTHOR_ID)).isEqualTo(1);
    }

    @DisplayName("Должен получить автора по идентификатору")
    @Test
    void getById() {
        Author author = createAuthor();
        when(authorDao.getById(EXPECTED_AUTHOR_ID)).thenReturn(author);
        assertThat(authorService.getById(EXPECTED_AUTHOR_ID)).isEqualTo(author);
    }

    @DisplayName("Должен получить автора по сокращению")
    @Test
    void getByBrief() {
        Author author = createAuthor();
        when(authorDao.getByBrief(author.getBrief())).thenReturn(author);
        assertThat(authorService.getByBrief(author.getBrief())).isEqualTo(author);
    }

    @DisplayName("Должен получить всех авторов")
    @Test
    void getAll() {
        List<Author> authors = List.of(createAuthor());
        when(authorDao.getAll()).thenReturn(authors);
        assertThat(authorService.getAll()).isEqualTo(authors);
    }

    private Author createAuthor() {
        return Author
                .builder()
                .id(1L)
                .brief("Ivanov I.")
                .lastName("Ivanov")
                .firstName("Ivan")
                .build();
    }

}