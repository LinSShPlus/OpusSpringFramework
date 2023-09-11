package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Student;
import ru.otus.spring.reader.BaseReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * StudentServiceImplTest
 **/
@DisplayName("Класс StudentServiceImpl")
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private BaseReader<String> inputReader;
    @InjectMocks
    private StudentServiceImpl studentService;

    @DisplayName("должен прочитать имя и фамилию студента")
    @Test
    void getStudent() {
        when(inputReader.read()).thenReturn("Ivan", "Petrov");

        Student student = studentService.getStudent();

        assertThat(student.getFirstName()).isEqualTo("Ivan");
        assertThat(student.getLastName()).isEqualTo("Petrov");
        verify(inputReader, times(2)).read();
    }

}