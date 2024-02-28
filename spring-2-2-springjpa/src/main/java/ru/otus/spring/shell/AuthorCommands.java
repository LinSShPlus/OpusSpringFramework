package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * AuthorCommands
 **/
@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    @ShellMethod(value = "Create a author", key = {"ai", "createAuthor"})
    public String createAuthor(String brief, String lastName, String firstName) {
        Author author = Author
                .builder()
                .brief(brief)
                .lastName(lastName)
                .firstName(firstName)
                .build();
        return String.format("Create a author with id = %d%n", authorService.save(author));
    }

    @ShellMethod(value = "Update the author", key = {"au", "updateAuthor"})
    public String updateAuthor(long id, String brief, String lastName, String firstName) {
        Author author = Author
                .builder()
                .id(id)
                .brief(brief)
                .lastName(lastName)
                .firstName(firstName)
                .build();
        long actualId = authorService.save(author);
        if (actualId > 0)
            return String.format("Update the author with id = %d%n", id);
        else
            return String.format("The author with id = %d is not found%n", id);
    }

    @ShellMethod(value = "Delete then author by id", key = {"ad", "deleteAuthor"})
    public String deleteAuthorById(long id) {
        int rows = authorService.deleteById(id);
        if (rows == 1)
            return String.format("Author with :id was deleted %d%n", id);
        else
            return String.format("The author with id = %d is not found%n", id);
    }

    @ShellMethod(value = "Get a author by id", key = {"ag", "getAuthor"})
    public String getAuthorById(long id) {
        Author author = authorService.getById(id).orElse(null);
        return String.format("%s%n", author);
    }

    @ShellMethod(value = "Get a author by brief", key = {"ab", "getAuthorByBrief"})
    public String getAuthorByBrief(String brief) {
        return String.format("%s%n", authorService.getByBrief(brief));
    }

    @ShellMethod(value = "Get all authors", key = {"a", "authors"})
    public String getAllAuthors() {
        List<Author> authors = authorService.getAll();
        if (!CollectionUtils.isEmpty(authors)) {
            return String.format(
                    "All authors: %n%s%n",
                    authors.stream().map(Objects::toString).collect(Collectors.joining("\n")));
        }
        return "Authors are not found";
    }

}
