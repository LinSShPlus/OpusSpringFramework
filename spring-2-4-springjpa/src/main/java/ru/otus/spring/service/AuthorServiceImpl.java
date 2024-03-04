package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * AuthorServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public long save(Author author) {
        return authorDao.save(author).getId();
    }

    @Override
    public int deleteById(long id) {
        return authorDao.deleteById(id);
    }

    @Override
    public Optional<Author> getById(long id) {
        return authorDao.findById(id);
    }

    @Override
    public Author getByBrief(String brief) {
        return authorDao.findByBrief(brief);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.findAll();
    }

}
