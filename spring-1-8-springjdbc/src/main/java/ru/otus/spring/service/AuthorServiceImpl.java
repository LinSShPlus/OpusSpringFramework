package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.List;

/**
 * AuthorServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public long insert(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public int update(Author author) {
        return authorDao.update(author);
    }

    @Override
    public int deleteById(long id) {
        return authorDao.deleteById(id);
    }

    @Override
    public Author getById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public Author getByBrief(String brief) {
        return authorDao.getByBrief(brief);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

}
