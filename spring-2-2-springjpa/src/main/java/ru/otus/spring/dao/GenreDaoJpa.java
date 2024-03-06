package ru.otus.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.util.*;

/**
 * GenreDaoJpa
 **/
@RequiredArgsConstructor
@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Genre save(Genre genre) {
        if (Objects.isNull(genre.getId())) {
            entityManager.persist(genre);
            return genre;
        } else {
            return entityManager.merge(genre);
        }
    }

    @Override
    public int deleteById(long id) {
        Query query = entityManager.createQuery("delete from Genre where id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public Genre findByBrief(String brief) {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g where g.brief = :brief", Genre.class);
        query.setParameter("brief", brief);
        return query.getSingleResult();
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

}
