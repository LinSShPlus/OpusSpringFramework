package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Student;
import ru.otus.spring.reader.BaseReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * StudentServiceImplTest
 **/
@DisplayName("Класс StudentServiceImpl")
@SpringBootTest
class StudentServiceImplTest {

    @MockBean
    private BaseReader<String> inputReader;
    @Autowired
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