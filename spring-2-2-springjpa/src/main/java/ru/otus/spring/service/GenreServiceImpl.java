package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * GenreServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public long save(Genre genre) {
        return genreDao.save(genre).getId();
    }

    @Override
    public int deleteById(long id) {
        return genreDao.deleteById(id);
    }

    @Override
    public Optional<Genre> getById(long id) {
        return genreDao.findById(id);
    }

    @Override
    public Genre getByBrief(String brief) {
        return genreDao.findByBrief(brief);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.findAll();
    }

}
