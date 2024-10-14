package ru.otus.spring.service;

import ru.otus.spring.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * UserService
 **/
public interface UserService {

    User save(User book);

    void deleteById(long id);

    Optional<User> getById(long id);

    User getByLogin(String brief);

    List<User> getAll();

}
