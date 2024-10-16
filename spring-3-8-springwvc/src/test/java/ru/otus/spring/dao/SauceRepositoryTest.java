package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Sauce;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SauceRepositoryTest
 **/
@DisplayName("Класс SauceRepository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
class SauceRepositoryTest {

    private static final long EXPECTED_SAUCE_ID = 1L;

    @Autowired
    private SauceRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Добавить соус")
    @Test
    void insert() {
        Sauce sauce = getNewSauce();
        Long id = repository.save(sauce).getId();
        assertThat(id).isNotNull().isPositive();
    }

    @DisplayName("Изменить соус")
    @Test
    void update() {
        Sauce expectedSauce = getExpectedSauce();
        Sauce actualSauce = repository.save(expectedSauce);
        assertThat(actualSauce).usingRecursiveComparison().isEqualTo(expectedSauce);
    }

    @DisplayName("Удалить соус по идентификатору")
    @Test
    void deleteById() {
        repository.deleteById(EXPECTED_SAUCE_ID);
        em.flush();
        Sauce actualSauce = em.find(Sauce.class, EXPECTED_SAUCE_ID);
        assertThat(actualSauce).isNull();
    }

    @DisplayName("Вернуть соус по идентификатору")
    @Test
    void findById() {
        Sauce expectedSauce = getExpectedSauce();
        Optional<Sauce> optionalActualSauce = repository.findById(expectedSauce.getId());
        assertThat(optionalActualSauce).isPresent().usingRecursiveComparison().isEqualTo(Optional.of(expectedSauce));
    }

    @DisplayName("Вернуть соус по сокращению")
    @Test
    void findByBrief() {
        Sauce expectedSauce = getExpectedSauce();
        Sauce actualSauce = repository.findByBrief(expectedSauce.getBrief());
        assertThat(actualSauce).isNotNull().usingRecursiveComparison().isEqualTo(expectedSauce);
    }

    @DisplayName("Вернуть все соусы")
    @Test
    void findAll() {
        List<Sauce> sauces = repository.findAll();
        assertThat(sauces).size().isEqualTo(repository.count());
    }

    private Sauce getNewSauce() {
        return Sauce
                .builder()
                .brief("brief")
                .name("name")
                .build();
    }

    private Sauce getExpectedSauce() {
        return em.find(Sauce.class, EXPECTED_SAUCE_ID);
    }

}