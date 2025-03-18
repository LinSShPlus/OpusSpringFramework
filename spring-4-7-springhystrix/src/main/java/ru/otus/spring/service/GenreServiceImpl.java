package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.GenreRepository;
import ru.otus.spring.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * GenreServiceImpl
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @HystrixCommand(commandKey = "save", fallbackMethod = "buildFallbackGenre")
    @Transactional
    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @HystrixCommand(commandKey = "deleteById", fallbackMethod = "buildFallbackDeleteById")
    @Transactional
    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    @HystrixCommand(commandKey = "getById", fallbackMethod = "buildFallbackGetById")
    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getById(long id) {
        return genreRepository.findById(id);
    }

    @HystrixCommand(commandKey = "getByBrief", fallbackMethod = "buildFallbackGetByBrief")
    @Transactional(readOnly = true)
    @Override
    public Genre getByBrief(String brief) {
        return genreRepository.findByBrief(brief);
    }

    @HystrixCommand(commandKey = "getAll", fallbackMethod = "buildFallbackGenres")
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    public Genre buildFallbackGenre(Genre genre) {
        return genre;
    }

    public void buildFallbackDeleteById(long id) {
        log.info("Delete by id: " + id);
    }

    public Optional<Genre> buildFallbackGetById(long id) {
        Genre genre = Genre
                .builder()
                .id(id)
                .brief("N/A")
                .name("N/A")
                .build();
        return Optional.ofNullable(genre);
    }

    public Genre buildFallbackGetByBrief(String brief) {
        return Genre
                .builder()
                .id(0L)
                .brief(brief)
                .name("N/A")
                .build();
    }

    public List<Genre> buildFallbackGenres() {
        return Collections.emptyList();
    }

}
