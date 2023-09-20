package ru.otus.spring.service;

import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * OutputServiceImplTest
 **/
@DisplayName("Класс OutputServiceImpl")
@ExtendWith(MockitoExtension.class)
class OutputServiceImplTest {

    @InjectMocks
    private OutputServiceImpl outputService;

    @DisplayName("должен вывести строку в лог")
    @Test
    void output() {
        String message = "Test string";

        LogCaptor logCaptor = LogCaptor.forClass(OutputServiceImpl.class);

        outputService.output(message);

        assertThat(logCaptor.getLogs()).hasSize(1).contains(message);
    }

}