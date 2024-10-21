package ru.otus.spring.service;

import ru.otus.spring.domain.AuthResponse;
import ru.otus.spring.domain.User;

/**
 * AuthService
 **/
public interface AuthService {

    AuthResponse getToken(User user);

}
