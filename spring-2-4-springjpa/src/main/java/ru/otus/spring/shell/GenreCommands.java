package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * GenreCommands
 **/
@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    @ShellMethod(value = "Create a genre", key = {"gi", "createGenre"})
    public String createGenre(String brief, String name) {
        Genre genre = Genre
                .builder()
                .brief(brief)
                .name(name)
                .build();
        return String.format("Create a genre with id = %d%n", genreService.save(genre));
    }

    @ShellMethod(value = "Update the genre", key = {"gu", "updateGenre"})
    public String updateGenre(long id, String brief, String name) {
        Genre genre = Genre
                .builder()
                .id(id)
                .brief(brief)
                .name(name)
                .build();
        long actualId = genreService.save(genre);
        if (actualId > 0)
            return String.format("Update the genre with id = %d%n", id);
        else
            return String.format("The genre with id = %d is not found%n", id);
    }

    @ShellMethod(value = "Delete then genre by id", key = {"gd", "deleteGenre"})
    public String deleteGenreById(long id) {
        genreService.deleteById(id);
        return String.format("Genre with :id was deleted %d%n", id);
    }

    @ShellMethod(value = "Get a genre by id", key = {"gg", "getGenre"})
    public String getGenreById(long id) {
        Genre genre = genreService.getById(id).orElse(null);
        return String.format("%s%n", genre);
    }

    @ShellMethod(value = "Get a genre by brief", key = {"gb", "getGenreByBrief"})
    public String getGenreByBrief(String brief) {
        return String.format("%s%n", genreService.getByBrief(brief));
    }

    @ShellMethod(value = "Get all genres", key = {"g", "genres"})
    public String getAllGenres() {
        List<Genre> genres = genreService.getAll();
        if (!CollectionUtils.isEmpty(genres)) {
            return String.format(
                    "All genres: %n%s%n",
                    genres.stream().map(Objects::toString).collect(Collectors.joining("\n")));
        }
        return "Genres are not found";
    }

}
