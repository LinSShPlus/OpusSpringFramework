package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

/**
 * AuthorRepository
 **/
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByBrief(String brief);

}
