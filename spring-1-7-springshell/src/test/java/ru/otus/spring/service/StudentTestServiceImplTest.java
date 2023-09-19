package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.printer.BasePrinter;
import ru.otus.spring.reader.BaseReader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * StudentTestServiceImplTest
 **/
@DisplayName("Класс StudentTestServiceImpl")
@SpringBootTest
class StudentTestServiceImplTest {

    @MockBean
    private BaseReader<List<String[]>> dataReader;
    @MockBean
    private BaseReader<String> inputReader;
    @Qualifier("studentTestPrinter")
    @MockBean
    private BasePrinter printer;
    @Autowired
    private StudentTestServiceImpl studentTestService;

    @DisplayName("должен вывести вопросы для тестирования студентов")
    @Test
    void getTest() {
        List<String[]> dataList = new ArrayList<>();
        dataList.add(new String[] {
                "1", "Who has invented the electric bulb?", "4",
                "Rudolf Diesel", "Benjamin Franklin", "Alexander G. Bell", "Thomas Alva Edison"
        });

        when(dataReader.read()).thenReturn(dataList);

        List<StudentTest> studentTestList = studentTestService.getTest();
        StudentTest studentTest = studentTestList.get(0);

        assertEquals(1, studentTestList.size());
        assertEquals(1, studentTest.getId());
        assertEquals("Who has invented the electric bulb?", studentTest.getQuestion());
        assertEquals(4, studentTest.getRightAnswer());
        assertEquals(List.of("Rudolf Diesel", "Benjamin Franklin", "Alexander G. Bell", "Thomas Alva Edison"), studentTest.getAnswers());
    }

    @DisplayName("должен вывести определенное количество вопросов из списка")
    @Test
    void getLimitTest() {
        List<StudentTest> studentTestList = new ArrayList<>();
        studentTestList.add(createStudentTest());
        studentTestList.add(createStudentTest());

        List<StudentTest> limitStudentTestList = studentTestService.getTest(studentTestList, 1);

        assertEquals(1, limitStudentTestList.size());
    }

    @DisplayName("должен вывести вопросы для тестирования студентов")
    @Test
    void printTest() {
        List<StudentTest> studentTestList = List.of(createStudentTest());

        studentTestService.printTest(studentTestList);

        verify(printer, times(1)).print(studentTestList);
    }

    @DisplayName("должен получить корректный ответ на вопрос из теста для студентов")
    @Test
    void getValidAnswer() {
        StudentTest studentTest = createStudentTest();

        when(inputReader.read()).thenReturn("3");

        short answer = studentTestService.getAnswer(studentTest);

        assertEquals(3, answer);
        verify(inputReader, times(1)).read();
    }

    @DisplayName("должен получить корректный ответ после серии некорректных ответов на вопрос из теста для студентов")
    @Test
    void getNoValidAnswer() {
        StudentTest studentTest = createStudentTest();

        when(inputReader.read()).thenReturn("Ok", "7", "2");

        short answer = studentTestService.getAnswer(studentTest);

        assertEquals(2, answer);
        verify(inputReader, times(3)).read();
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