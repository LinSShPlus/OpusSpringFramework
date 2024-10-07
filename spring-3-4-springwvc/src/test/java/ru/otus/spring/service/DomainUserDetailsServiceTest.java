package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * DomainUserDetailsServiceTest
 **/
@DisplayName("Класс DomainUserDetailsServiceTest")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DomainUserDetailsServiceTest {

    private static final String USER_VALID_LOGIN = "admin";
    private static final String USER_INVALID_LOGIN = "demo";

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void assertThatUserCanBeFoundByLogin() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_VALID_LOGIN);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_VALID_LOGIN);
    }

    @Test
    void assertThatUserCanNotBeFoundByLogin() {
        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(USER_INVALID_LOGIN)
        );
    }

}
