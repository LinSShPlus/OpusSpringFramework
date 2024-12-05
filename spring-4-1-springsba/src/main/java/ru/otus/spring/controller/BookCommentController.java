package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.BookCommentService;

import java.util.List;
import java.util.Optional;

/**
 * BookCommentController
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BookCommentController {

    private final BookCommentService bookCommentService;

    @PostMapping("/bookcomment")
    public BookComment saveBookComment(@RequestBody BookComment bookComment) {
        return bookCommentService.save(bookComment);
    }

    @PutMapping("/bookcomment/{id}")
    public BookComment updateBookComment(@PathVariable("id") long id, @RequestBody BookComment bookComment) {
        Optional<BookComment> actualBookComment = bookCommentService.getById(id);
        if (actualBookComment.isEmpty())
            throw new NotFoundException("BookComment with id = [" + id + "] not found");
        bookComment.setId(id);
        return bookCommentService.save(bookComment);
    }


    @DeleteMapping("/bookcomment/{id}")
    public void deleteBookCommentById(@PathVariable("id") long id) {
        bookCommentService.deleteById(id);
    }

    @GetMapping("/bookcomment/{id}")
    public BookComment getBookCommentById(@PathVariable("id") long id) {
        return bookCommentService.getById(id).orElseThrow(() -> new NotFoundException("BookComment with id = [" + id + "] not found"));
    }

    @GetMapping(value = "/bookcomment", params = "bookId")
    public List<BookComment> getBookCommentByBookId(@RequestParam("bookId") long bookId) {
        return bookCommentService.getByBookId(bookId);
    }

}
