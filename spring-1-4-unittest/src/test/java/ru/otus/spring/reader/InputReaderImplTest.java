package ru.otus.spring.reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * InputReaderImplTest
 **/
@DisplayName("Класс InputReaderImpl")
@ExtendWith(MockitoExtension.class)
class InputReaderImplTest {

    @InjectMocks
    private InputReaderImpl inputReader;

    @DisplayName("должен прочитать входные данные")
    @Test
    void read() {
        String input = "input read data";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals(input, inputReader.read());

        System.setIn(System.in);
    }

}