package ru.otus.spring.dao;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.util.*;

/**
 * BookDaoJpa
 **/
@RequiredArgsConstructor
@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Book save(Book book) {
        if (Objects.isNull(book.getId())) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public int deleteById(long id) {
        Query query = entityManager.createQuery("delete from Book where id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public Book findByBrief(String brief) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b where b.brief = :brief", Book.class);
        query.setParameter("brief", brief);
        return query.getSingleResult();
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class);
        return query.getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }

}
