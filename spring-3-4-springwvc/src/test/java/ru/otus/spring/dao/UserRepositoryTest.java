package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserRepositoryTest
 **/
@DisplayName("Класс UserRepository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
class UserRepositoryTest {

    private static final long EXPECTED_USER_ID = 1L;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить пользователя")
    @Test
    void insert() {
        User user = getNewUser();
        Long id = repository.save(user).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить пользователя")
    @Test
    void update() {
        User expectedUser = getExpectedUser();
        User actualUser = repository.save(expectedUser);
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @DisplayName("Удалить пользователя по идентификатору")
    @Test
    void deleteById() {
        repository.deleteById(EXPECTED_USER_ID);
        em.flush();
        User actualUser = em.find(User.class, EXPECTED_USER_ID);
        assertThat(actualUser).isNull();
    }

    @DisplayName("Вернуть пользователя по идентификатору")
    @Test
    void findById() {
        User expectedUser = getExpectedUser();
        Optional<User> optionalActualUser = repository.findById(expectedUser.getId());
        assertThat(optionalActualUser).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedUser));
    }

    @DisplayName("Вернуть пользователя по логину")
    @Test
    void findByLogin() {
        User expectedUser = getExpectedUser();
        User actualUser = repository.findByLogin(expectedUser.getLogin());
        assertThat(actualUser).isNotNull().usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @DisplayName("Вернуть всех пользователей")
    @Test
    void findAll() {
        List<User> users = repository.findAll();
        assertThat(users).size().isEqualTo(repository.count());
    }

    private User getNewUser() {
        return User
                .builder()
                .id(EXPECTED_USER_ID)
                .login("testUser")
                .role("USER")
                .password("encrypted_password")
                .enabled(true)
                .build();
    }

    private User getExpectedUser() {
        return em.find(User.class, EXPECTED_USER_ID);
    }

}
