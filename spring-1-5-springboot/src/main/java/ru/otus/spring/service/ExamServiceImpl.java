package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.StudentTestConfig;
import ru.otus.spring.domain.Exam;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.StudentTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ExamServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class ExamServiceImpl implements ExamService {

    private final StudentTestService studentTestService;
    private final StudentService studentService;
    private final StudentTestConfig studentTestConfig;
    private final OutputService outputService;

    @Override
    public void runExam() {
        AtomicInteger rightAnswersCount = new AtomicInteger();
        Student student = studentService.getStudent();
        final int allAnswersCount = studentTestConfig.getAllAnswersCount();
        final int minAnswersCount = studentTestConfig.getMinAnswersCount();
        List<StudentTest> studentTestList = studentTestService.getTest(studentTestService.getTest(), allAnswersCount);

        studentTestList.forEach(t -> {
            short answer = studentTestService.getAnswer(t);
            if (answer == t.getRightAnswer())
                rightAnswersCount.getAndIncrement();
        });

        Exam exam = new Exam();
        exam.setStudent(student);
        exam.setAllAnswersCount(allAnswersCount);
        exam.setRightAnswersCount(rightAnswersCount.get());
        exam.setWrongAnswersCount(allAnswersCount - rightAnswersCount.get());
        exam.setPassed(rightAnswersCount.get() >= minAnswersCount);

        outputService.output(exam);
    }

}
