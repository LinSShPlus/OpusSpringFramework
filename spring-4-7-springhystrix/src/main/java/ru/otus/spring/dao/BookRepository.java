package ru.otus.spring.dao;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.util.List;

/**
 * BookRepository
 **/
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByBrief(String brief);

    @NonNull
    @EntityGraph(attributePaths = {"author", "genre"})
    @Override
    List<Book> findAll();

}
