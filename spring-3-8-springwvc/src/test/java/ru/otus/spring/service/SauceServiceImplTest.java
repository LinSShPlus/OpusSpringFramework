package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.SauceRepository;
import ru.otus.spring.domain.Sauce;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * SauceServiceImplTest
 **/
@DisplayName("Класс SauceServiceImpl")
@ExtendWith(MockitoExtension.class)
class SauceServiceImplTest {

    private static final long EXPECTED_SAUCE_ID = 1L;

    @Mock
    private SauceRepository sauceRepository;
    @InjectMocks
    private SauceServiceImpl sauceService;

    @DisplayName("Должен сохранить соус")
    @Test
    void save() {
        Sauce sauce = createSauce();
        when(sauceRepository.save(sauce)).thenReturn(sauce);
        assertThat(sauceService.save(sauce)).isEqualTo(sauce);
    }

    @DisplayName("Должен удалить соус по идентификатору")
    @Test
    void deleteById() {
        sauceService.deleteById(EXPECTED_SAUCE_ID);
        verify(sauceRepository, times(1)).deleteById(EXPECTED_SAUCE_ID);
    }

    @DisplayName("Должен получить соус по идентификатору")
    @Test
    void getById() {
        Optional<Sauce> sauce = Optional.of(createSauce());
        when(sauceRepository.findById(EXPECTED_SAUCE_ID)).thenReturn(sauce);
        assertThat(sauceService.getById(EXPECTED_SAUCE_ID)).isEqualTo(sauce);
    }

    @DisplayName("Должен получить соус по сокращению")
    @Test
    void getByBrief() {
        Sauce sauce = createSauce();
        when(sauceRepository.findByBrief(sauce.getBrief())).thenReturn(sauce);
        assertThat(sauceService.getByBrief(sauce.getBrief())).isEqualTo(sauce);
    }

    @DisplayName("Должен получить все соусы")
    @Test
    void getAll() {
        List<Sauce> sauces = List.of(createSauce());
        when(sauceRepository.findAll()).thenReturn(sauces);
        assertThat(sauceService.getAll()).isEqualTo(sauces);
    }

    private Sauce createSauce() {
        return Sauce
                .builder()
                .id(EXPECTED_SAUCE_ID)
                .brief("ketchup")
                .name("ketchup")
                .build();
    }

}
