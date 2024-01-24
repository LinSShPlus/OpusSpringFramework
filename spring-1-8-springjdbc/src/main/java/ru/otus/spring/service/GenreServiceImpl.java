package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.List;

/**
 * GenreServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public long insert(Genre genre) {
        return genreDao.insert(genre);
    }

    @Override
    public int update(Genre genre) {
        return genreDao.update(genre);
    }

    @Override
    public int deleteById(long id) {
        return genreDao.deleteById(id);
    }

    @Override
    public Genre getById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public Genre getByBrief(String brief) {
        return genreDao.getByBrief(brief);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

}
