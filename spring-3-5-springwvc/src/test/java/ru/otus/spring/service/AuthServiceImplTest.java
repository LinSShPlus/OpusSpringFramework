package ru.otus.spring.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.domain.AuthResponse;
import ru.otus.spring.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * AuthServiceImplTest
 **/
@DisplayName("Класс AuthServiceImpl")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class AuthServiceImplTest {

    @Autowired
    private AuthServiceImpl authService;

    @Test
    void getToken() {
        User user = createUser("user", "password");
        AuthResponse authResponse = authService.getToken(user);
        assertThat(authResponse).isNotNull();
        assertThat(StringUtils.isNotEmpty(authResponse.getToken())).isTrue();
        assertThat(authResponse.getTokenType()).isEqualTo(AuthResponse.TOKEN_TYPE_BEARER);
    }

    @Test
    void getTokenWithUsernameNotFound() {
        User user = createUser("invalid", "password");
        assertThrows(UsernameNotFoundException.class, () -> authService.getToken(user));
    }

    @Test
    void getTokenWithUserDisabled() {
        User user = createUser("test", "password");
        assertThrows(DisabledException.class, () -> authService.getToken(user));
    }

    @Test
    void getTokenWithUserInvalidPassword() {
        User user = createUser("user", "testpass");
        assertThrows(BadCredentialsException.class, () -> authService.getToken(user));
    }

    private User createUser(String login, String password) {
        return User
                .builder()
                .login(login)
                .password(password)
                .build();
    }

}
