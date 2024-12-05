package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.GenreRepository;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * GenreServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getById(long id) {
        return genreRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Genre getByBrief(String brief) {
        return genreRepository.findByBrief(brief);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

}
