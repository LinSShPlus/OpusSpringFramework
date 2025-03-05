package ru.otus.spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.*;
import ru.otus.spring.message.MessageSender;
import ru.otus.spring.model.LibraryMessage;

/**
 * ProducerServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class ProducerServiceImpl implements ProducerService {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final BookCommentService bookCommentService;
    private final MessageSender messageSender;
    private final ObjectMapper objectMapper;

    @Override
    public void start() {
        sendAuthor();
        sendGenre();
        sendBook();
        sendBookComment();
    }

    private void sendAuthor() {
        String objectType = Author.class.getName();
        messageSender.send(createMessage(objectType, authorService.getAll(), "Get all authors"));
        messageSender.send(createMessage(objectType, authorService.getById(2L), "Get author by id: 2"));
        messageSender.send(createMessage(objectType, authorService.getByBrief("Ivanov I."), "Get author by brief: Ivanov I."));
    }

    private void sendGenre() {
        String objectType = Genre.class.getName();
        messageSender.send(createMessage(objectType, genreService.getAll(), "Get all genres"));
    }

    private void sendBook() {
        String objectType = Book.class.getName();
        messageSender.send(createMessage(objectType, bookService.getAll(), "Get all books"));
    }

    private void sendBookComment() {
        String objectType = BookComment.class.getName();
        messageSender.send(createMessage(objectType, bookCommentService.getByBookId(1L), "Get book comments by bookId: 1"));
    }

    private LibraryMessage createMessage(String objectType, Object object, String comment) {
        return LibraryMessage
                .builder()
                .objectType(objectType)
                .object(objectMapper.valueToTree(object))
                .comment(comment)
                .build();
    }

}
