package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * BookCommands
 **/
@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @ShellMethod(value = "Create a book", key = {"bi", "createBook"})
    public String createBook(String brief, String title, String text, long authorId, long genreId) {
        Author author = authorService.getById(authorId).orElse(null);
        Genre genre = genreService.getById(genreId).orElse(null);
        Book book = Book
                .builder()
                .brief(brief)
                .title(title)
                .text(text)
                .author(author)
                .genre(genre)
                .build();
        return String.format("Create a book with id = %d%n", bookService.save(book));
    }

    @ShellMethod(value = "Update the book", key = {"bu", "updateBook"})
    public String updateBook(long id, String brief, String title, String text, long authorId, long genreId) {
        Author author = authorService.getById(authorId).orElse(null);
        Genre genre = genreService.getById(genreId).orElse(null);
        Book book = Book
                .builder()
                .id(id)
                .brief(brief)
                .title(title)
                .text(text)
                .author(author)
                .genre(genre)
                .build();
        long actualId = bookService.save(book);
        if (actualId > 0)
            return String.format("Update the book with id = %d%n", id);
        else
            return String.format("The book with id = %d is not found%n", id);
    }

    @ShellMethod(value = "Delete then book by id", key = {"bd", "deleteBook"})
    public String deleteBookById(long id) {
        int rows = bookService.deleteById(id);
        if (rows == 1)
            return String.format("Book with :id was deleted %d%n", id);
        else
            return String.format("The book with id = %d is not found%n", id);
    }

    @ShellMethod(value = "Get a book by id", key = {"bg", "getBook"})
    public String getBookById(long id) {
        Book book = bookService.getById(id).orElse(null);
        return String.format("%s%n", book);
    }

    @ShellMethod(value = "Get a book by brief", key = {"bb", "getBookByBrief"})
    public String getBookByBrief(String brief) {
        return String.format("%s%n", bookService.getByBrief(brief));
    }

    @ShellMethod(value = "Get all books", key = {"b", "books"})
    public String getAllBooks() {
        List<Book> books = bookService.getAll();
        if (!CollectionUtils.isEmpty(books)) {
            return String.format(
                    "All books: %n%s%n",
                    books.stream().map(Objects::toString).collect(Collectors.joining("\n")));
        }
        return "Books are not found";
    }

    @ShellMethod(value = "Get the number of books", key = {"bc", "countBooks"})
    public String getBooksCount() {
        return String.format("Number of books = %d%n", bookService.count());
    }

}
