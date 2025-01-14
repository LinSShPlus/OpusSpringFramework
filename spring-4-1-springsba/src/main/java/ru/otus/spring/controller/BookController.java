package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.Optional;

/**
 * BookController
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public Book saveBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/book/{id}")
    public Book updateBook(@PathVariable("id") long id, @RequestBody Book book) {
        Optional<Book> actualBook = bookService.getById(id);
        if (actualBook.isEmpty())
            throw new NotFoundException("Book with id = [" + id + "] not found");
        book.setId(id);
        return bookService.save(book);
    }


    @DeleteMapping("/book/{id}")
    public void deleteBookById(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable("id") long id) {
        return bookService.getById(id).orElseThrow(() -> new NotFoundException("Book with id = [" + id + "] not found"));
    }

    @GetMapping(value = "/book", params = "brief")
    public Book getBookByBrief(@RequestParam("brief") String brief) {
        return bookService.getByBrief(brief);
    }

    @GetMapping("/book")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

}
