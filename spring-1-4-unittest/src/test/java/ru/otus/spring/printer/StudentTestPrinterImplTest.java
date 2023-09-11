package ru.otus.spring.printer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.service.OutputService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * StudentTestPrinterImplTest
 **/
@DisplayName("Класс StudentTestPrinterImpl")
@ExtendWith(MockitoExtension.class)
class StudentTestPrinterImplTest {

    @Mock
    private OutputService outputService;
    @InjectMocks
    private StudentTestPrinterImpl printer;

    @DisplayName("должен вывести вопросы для тестирования студентов из CSV-файла")
    @Test
    void print() {
        StudentTest studentTest = createStudentTest();
        String message = studentTest.toString();

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

        printer.print(List.of(studentTest));

        verify(outputService).output(captor.capture());
        assertEquals(captor.getValue(), message);
    }

    private StudentTest createStudentTest() {
        StudentTest studentTest = new StudentTest();
        studentTest.setId(1);
        studentTest.setQuestion("Who has invented the electric bulb?");
        studentTest.setRightAnswer((short) 4);
        studentTest.setAnswers(List.of("Rudolf Diesel", "Benjamin Franklin", "Alexander G. Bell", "Thomas Alva Edison"));
        return studentTest;
    }

}
