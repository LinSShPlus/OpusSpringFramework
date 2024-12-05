package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.Optional;

/**
 * GenreController
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/genre")
    public Genre saveGenre(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    @PutMapping("/genre/{id}")
    public Genre updateGenre(@PathVariable("id") long id, @RequestBody Genre genre) {
        Optional<Genre> actualGenre = genreService.getById(id);
        if (actualGenre.isEmpty())
            throw new NotFoundException("Genre with id = [" + id + "] not found");
        genre.setId(id);
        return genreService.save(genre);
    }


    @DeleteMapping("/genre/{id}")
    public void deleteGenreById(@PathVariable("id") long id) {
        genreService.deleteById(id);
    }

    @GetMapping("/genre/{id}")
    public Genre getGenreById(@PathVariable("id") long id) {
        return genreService.getById(id).orElseThrow(() -> new NotFoundException("Genre with id = [" + id + "] not found"));
    }

    @GetMapping(value = "/genre", params = "brief")
    public Genre getGenreByBrief(@RequestParam("brief") String brief) {
        return genreService.getByBrief(brief);
    }

    @GetMapping("/genre")
    public List<Genre> getAllGenres() {
        return genreService.getAll();
    }

}
