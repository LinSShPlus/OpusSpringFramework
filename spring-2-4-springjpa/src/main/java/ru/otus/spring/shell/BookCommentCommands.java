package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.service.BookCommentService;
import ru.otus.spring.service.BookService;

import java.util.List;

/**
 * BookCommentCommands
 **/
@RequiredArgsConstructor
@ShellComponent
public class BookCommentCommands {

    private final BookCommentService bookCommentService;
    private final BookService bookService;

    @ShellMethod(value = "Create a comment on the book", key = {"bci", "createBookComment"})
    public String createBookComment(String comment, long bookId) {
        Book book = bookService.getById(bookId).orElse(null);
        BookComment bookComment = BookComment
                .builder()
                .comment(comment)
                .book(book)
                .build();
        return String.format("Create a comment on the book with id = %d%n", bookCommentService.save(bookComment));
    }

    @ShellMethod(value = "Update the comment on the book", key = {"bcu", "updateBookComment"})
    public String updateBookComment(long id, String comment, long bookId) {
        Book book = bookService.getById(bookId).orElse(null);
        BookComment bookComment = BookComment
                .builder()
                .id(id)
                .comment(comment)
                .book(book)
                .build();
        long actualId = bookCommentService.save(bookComment);
        if (actualId > 0)
            return String.format("Update the comment on the book with id = %d%n", id);
        else
            return String.format("The comment on the book with id = %d is not found%n", id);
    }

    @ShellMethod(value = "Delete then comment on the book by id", key = {"bcd", "deleteBookComment"})
    public String deleteBookCommentById(long id) {
        int rows = bookCommentService.deleteById(id);
        if (rows == 1)
            return String.format("Comment on the book with :id was deleted %d%n", id);
        else
            return String.format("The comment on the book with id = %d is not found%n", id);
    }

    @ShellMethod(value = "Get a comment on the book by id", key = {"bcg", "getBookComment"})
    public String getBookCommentById(long id) {
        BookComment bookComment = bookCommentService.getById(id).orElse(null);
        return String.format("%s%n", bookComment);
    }

    @ShellMethod(value = "Get a comments on the book by bookId", key = {"bcgi", "getBookCommentByBookId"})
    public String getBookCommentByBookId(long bookiId) {
        List<BookComment> bookComments = bookCommentService.getByBookId(bookiId);
        return String.format("%s%n", bookComments);
    }

}
