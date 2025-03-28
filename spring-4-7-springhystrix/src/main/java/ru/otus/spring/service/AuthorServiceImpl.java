package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.AuthorRepository;
import ru.otus.spring.domain.Author;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * AuthorServiceImpl
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @HystrixCommand(commandKey = "save", fallbackMethod = "buildFallbackAuthor")
    @Transactional
    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @HystrixCommand(commandKey = "deleteById", fallbackMethod = "buildFallbackDeleteById")
    @Transactional
    @Override
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    @HystrixCommand(commandKey = "getById", fallbackMethod = "buildFallbackGetById")
    @Transactional(readOnly = true)
    @Override
    public Optional<Author> getById(long id) {
        return authorRepository.findById(id);
    }

    @HystrixCommand(commandKey = "getByBrief", fallbackMethod = "buildFallbackGetByBrief")
    @Transactional(readOnly = true)
    @Override
    public Author getByBrief(String brief) {
        return authorRepository.findByBrief(brief);
    }

    @HystrixCommand(commandKey = "getAll", fallbackMethod = "buildFallbackAuthors")
    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author buildFallbackAuthor(Author author) {
        return author;
    }

    public void buildFallbackDeleteById(long id) {
        log.info("Delete by id: " + id);
    }

    public Optional<Author> buildFallbackGetById(long id) {
        Author author = Author
                .builder()
                .id(id)
                .brief("N/A")
                .firstName("N/A")
                .lastName("N/A")
                .build();
        return Optional.ofNullable(author);
    }

    public Author buildFallbackGetByBrief(String brief) {
        return Author
                .builder()
                .id(0L)
                .brief(brief)
                .firstName("N/A")
                .lastName("N/A")
                .build();
    }

    public List<Author> buildFallbackAuthors() {
        return Collections.emptyList();
    }

}
