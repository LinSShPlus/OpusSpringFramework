package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;

/**
 * MainServiceImpl
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final BookCommentService bookCommentService;

    @Override
    public void start() {
        processAuthor();
        processGenre();
        processBook();
        processBookComment();
    }

    private void processAuthor() {
        log.info("Save author: {}", authorService.save(
                Author.builder()
                        .brief("TestAuthor")
                        .firstName("Test")
                        .lastName("Testov")
                        .build()
        ));
        log.info("Get all authors: {}", authorService.getAll());
        log.info("Get author by id: {}", authorService.getById(2L));
        log.info("Get author by brief: {}", authorService.getByBrief("Ivanov I."));
        authorService.deleteById(authorService.getByBrief("TestAuthor").getId());
    }

    private void processGenre() {
        log.info("Get all genres: {}", genreService.getAll());
    }

    private void processBook() {
        log.info("Get all books: {}", bookService.getAll());
    }

    private void processBookComment() {
        log.info("Get book comments by bookId: {}", bookCommentService.getByBookId(1L));
    }

}
