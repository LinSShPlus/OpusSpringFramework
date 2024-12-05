package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.User;

/**
 * UserRepository
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

}
