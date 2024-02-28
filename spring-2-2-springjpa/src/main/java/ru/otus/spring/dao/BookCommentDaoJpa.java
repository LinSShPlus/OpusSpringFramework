package ru.otus.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * BookCommentDaoJpa
 **/
@RequiredArgsConstructor
@Repository
public class BookCommentDaoJpa implements BookCommentDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public BookComment save(BookComment bookComment) {
        if (Objects.isNull(bookComment.getId())) {
            entityManager.persist(bookComment);
            return bookComment;
        } else {
            return entityManager.merge(bookComment);
        }
    }

    @Override
    public int deleteById(long id) {
        Query query = entityManager.createQuery("delete from BookComment where id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public Optional<BookComment> findById(long id) {
        return Optional.ofNullable(entityManager.find(BookComment.class, id));
    }

    @Override
    public List<BookComment> findByBookId(long bookId) {
        TypedQuery<BookComment> query = entityManager.createQuery("select bc from BookComment bc where bc.book.id = :book_id", BookComment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

}
