package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.config.StudentTestConfig;
import ru.otus.spring.domain.Exam;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.StudentTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * ExamServiceImplTest
 **/
@DisplayName("Класс ExamServiceImpl")
@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

    @Mock
    private StudentTestService studentTestService;
    @Mock
    private StudentService studentService;
    @Mock
    private StudentTestConfig studentTestConfig;
    @Mock
    private OutputService outputService;
    @InjectMocks
    private ExamServiceImpl examService;

    @DisplayName("студент должен успешно пройти экзамен")
    @Test
    void runRightExam() {
        int allAnswersCount = 2;
        int minAnswersCount = 2;
        List<StudentTest> studentTestList = createStudentTestList();

        when(studentTestConfig.getAllAnswersCount()).thenReturn(allAnswersCount);
        when(studentTestConfig.getMinAnswersCount()).thenReturn(minAnswersCount);
        when(studentService.getStudent()).thenReturn(createStudent());
        when(studentTestService.getTest()).thenReturn(studentTestList);
        when(studentTestService.getTest(anyList(), anyInt())).thenReturn(studentTestList);
        when(studentTestService.getAnswer(any())).thenReturn((short) 4, (short) 3);
        ArgumentCaptor<Exam> objectCaptor = ArgumentCaptor.forClass(Exam.class);

        examService.runExam();

        verify(outputService).output(objectCaptor.capture());
        assertEquals(objectCaptor.getValue(), createExam(allAnswersCount, minAnswersCount, 2));
    }

    @DisplayName("студент должен неуспешно пройти экзамен")
    @Test
    void runWrongExam() {
        int allAnswersCount = 2;
        int minAnswersCount = 2;
        List<StudentTest> studentTestList = createStudentTestList();

        when(studentTestConfig.getAllAnswersCount()).thenReturn(allAnswersCount);
        when(studentTestConfig.getMinAnswersCount()).thenReturn(minAnswersCount);
        when(studentService.getStudent()).thenReturn(createStudent());
        when(studentTestService.getTest()).thenReturn(studentTestList);
        when(studentTestService.getTest(anyList(), anyInt())).thenReturn(studentTestList);
        when(studentTestService.getAnswer(any())).thenReturn((short) 1, (short) 2);
        ArgumentCaptor<Exam> objectCaptor = ArgumentCaptor.forClass(Exam.class);

        examService.runExam();

        verify(outputService).output(objectCaptor.capture());
        System.out.println(objectCaptor.getValue());
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