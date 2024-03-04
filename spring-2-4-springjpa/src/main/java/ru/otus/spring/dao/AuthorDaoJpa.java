package ru.otus.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.util.*;

/**
 * AuthorDaoJpa
 **/
@RequiredArgsConstructor
@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Author save(Author author) {
        if (Objects.isNull(author.getId())) {
            entityManager.persist(author);
            return author;
        } else {
            return entityManager.merge(author);
        }
    }

    @Override
    public int deleteById(long id) {
        Query query = entityManager.createQuery("delete from Author where id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public Author findByBrief(String brief) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.brief = :brief", Author.class);
        query.setParameter("brief", brief);
        return query.getSingleResult();
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

}
