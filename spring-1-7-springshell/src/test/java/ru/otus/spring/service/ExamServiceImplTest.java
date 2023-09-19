package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.domain.Exam;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.printer.BasePrinter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * ExamServiceImplTest
 **/
@DisplayName("Класс ExamServiceImpl")
@SpringBootTest
class ExamServiceImplTest {

    @MockBean
    private StudentTestService studentTestService;
    @MockBean
    private StudentService studentService;
    @MockBean
    private AppConfig appConfig;
    @MockBean
    private OutputService outputService;
    @Qualifier("examPrinter")
    @MockBean
    private BasePrinter printer;
    @Autowired
    private ExamServiceImpl examService;

    @DisplayName("студент должен успешно пройти экзамен")
    @Test
    void runRightExam() {
        int allAnswersCount = 2;
        int minAnswersCount = 2;
        List<StudentTest> studentTestList = createStudentTestList();

        when(appConfig.getAllAnswersCount()).thenReturn(allAnswersCount);
        when(appConfig.getMinAnswersCount()).thenReturn(minAnswersCount);
        when(studentService.getStudent()).thenReturn(createStudent());
        when(studentTestService.getTest()).thenReturn(studentTestList);
        when(studentTestService.getTest(anyList(), anyInt())).thenReturn(studentTestList);
        when(studentTestService.getAnswer(any())).thenReturn((short) 4, (short) 3);
        ArgumentCaptor<Exam> objectCaptor = ArgumentCaptor.forClass(Exam.class);

        examService.runExam();

        verify(printer).print(objectCaptor.capture());
        assertEquals(objectCaptor.getValue(), createExam(allAnswersCount, minAnswersCount, 2));
    }

    @DisplayName("студент должен неуспешно пройти экзамен")
    @Test
    void runWrongExam() {
        int allAnswersCount = 2;
        int minAnswersCount = 2;
        List<StudentTest> studentTestList = createStudentTestList();

        when(appConfig.getAllAnswersCount()).thenReturn(allAnswersCount);
        when(appConfig.getMinAnswersCount()).thenReturn(minAnswersCount);
        when(studentService.getStudent()).thenReturn(createStudent());
        when(studentTestService.getTest()).thenReturn(studentTestList);
        when(studentTestService.getTest(anyList(), anyInt())).thenReturn(studentTestList);
        when(studentTestService.getAnswer(any())).thenReturn((short) 1, (short) 2);
        ArgumentCaptor<Exam> objectCaptor = ArgumentCaptor.forClass(Exam.class);

        examService.runExam();

        verify(printer).print(objectCaptor.capture());
        assertEquals(objectCaptor.getValue(), createExam(allAnswersCount, minAnswersCount, 0));
    }

    private Exam createExam(int allAnswersCount, int minAnswersCount, int rightAnswersCount) {
        Exam exam = new Exam();
        exam.setStudent(createStudent());
        exam.setAllAnswersCount(allAnswersCount);
        exam.setRightAnswersCount(rightAnswersCount);
        exam.setWrongAnswersCount(allAnswersCount - rightAnswersCount);
        exam.setPassed(rightAnswersCount >= minAnswersCount);
        return exam;
    }

    private List<StudentTest> createStudentTestList() {
        StudentTest studentTest1 = new StudentTest();
        studentTest1.setId(1);
        studentTest1.setQuestion("Who has invented the electric bulb?");
        studentTest1.setRightAnswer((short) 4);
        studentTest1.setAnswers(List.of("Rudolf Diesel", "Benjamin Franklin", "Alexander G. Bell", "Thomas Alva Edison"));

        StudentTest studentTest2 = new StudentTest();
        studentTest2.setId(2);
        studentTest2.setQuestion("Which actress has won the most Oscars?");
        studentTest2.setRightAnswer((short) 3);
        studentTest2.setAnswers(List.of("Marilyn Monroe", "Sophie Lorain", "Katherine Hepburn", "Audrey Hepburn"));

        return List.of(studentTest1, studentTest2);
    }

    private Student createStudent() {
        return new Student("Ivan", "Petrov");
    }

}