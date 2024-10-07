package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.UserRepository;
import ru.otus.spring.domain.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * UserServiceImplTest
 **/
@DisplayName("Класс UserServiceImpl")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final long EXPECTED_USER_ID = 1L;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @DisplayName("Должен сохранить пользователя")
    @Test
    void save() {
        User user = createUser();
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.save(user)).isEqualTo(user);
    }

    @DisplayName("Должен удалить пользователя по идентификатору")
    @Test
    void deleteById() {
        userService.deleteById(EXPECTED_USER_ID);
        verify(userRepository, times(1)).deleteById(EXPECTED_USER_ID);
    }

    @DisplayName("Должен получить пользователя по идентификатору")
    @Test
    void getById() {
        Optional<User> user = Optional.of(createUser());
        when(userRepository.findById(EXPECTED_USER_ID)).thenReturn(user);
        assertThat(userService.getById(EXPECTED_USER_ID)).isEqualTo(user);
    }

    @DisplayName("Должен получить пользователя по логину")
    @Test
    void getByLogin() {
        User user = createUser();
        when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
        assertThat(userService.getByLogin(user.getLogin())).isEqualTo(user);
    }

    @DisplayName("Должен получить всех пользователей")
    @Test
    void getAll() {
        List<User> users = List.of(createUser());
        when(userRepository.findAll()).thenReturn(users);
        assertThat(userService.getAll()).isEqualTo(users);
    }

    private User createUser() {
        return User
                .builder()
                .id(EXPECTED_USER_ID)
                .login("testUser")
                .role("USER")
                .password("encrypted_password")
                .enabled(true)
                .build();
    }

}
