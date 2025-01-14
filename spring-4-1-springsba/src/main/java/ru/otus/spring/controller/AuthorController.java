package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.Optional;

/**
 * AuthorController
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/author")
    public Author saveAuthor(@RequestBody Author author) {
        return authorService.save(author);
    }

    @PutMapping("/author/{id}")
    public Author updateAuthor(@PathVariable("id") long id, @RequestBody Author author) {
        Optional<Author> actualAuthor = authorService.getById(id);
        if (actualAuthor.isEmpty())
            throw new NotFoundException("Author with id = [" + id + "] not found");
        author.setId(id);
        return authorService.save(author);
    }


    @DeleteMapping("/author/{id}")
    public void deleteAuthorById(@PathVariable("id") long id) {
        authorService.deleteById(id);
    }

    @GetMapping("/author/{id}")
    public Author getAuthorById(@PathVariable("id") long id) {
        return authorService.getById(id).orElseThrow(() -> new NotFoundException("Author with id = [" + id + "] not found"));
    }

    @GetMapping(value = "/author", params = "brief")
    public Author getAuthorByBrief(@RequestParam("brief") String brief) {
        return authorService.getByBrief(brief);
    }

    @GetMapping("/author")
    public List<Author> getAllAuthors() {
        return authorService.getAll();
    }

}
