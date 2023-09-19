package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.domain.Exam;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.exception.StudentTestRuntimeException;
import ru.otus.spring.printer.BasePrinter;
import ru.otus.spring.utils.I18nUtils;

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
    private final AppConfig appConfig;
    private final I18nUtils i18nUtils;
    @Qualifier("examPrinter")
    private final BasePrinter printer;

    @Override
    public void runExam() {
        AtomicInteger rightAnswersCount = new AtomicInteger();
        Student student = studentService.getStudent();
        final int allAnswersCount = appConfig.getAllAnswersCount();
        final int minAnswersCount = appConfig.getMinAnswersCount();
        List<StudentTest> studentTestList = studentTestService.getTest(studentTestService.getTest(), allAnswersCount);

        checkExamSettings(studentTestList);

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

        printer.print(exam);
    }

    private void checkExamSettings(List<StudentTest> studentTestList) {
        final int allAnswersCount = appConfig.getAllAnswersCount();
        final int minAnswersCount = appConfig.getMinAnswersCount();

        if (allAnswersCount < minAnswersCount)
            throw new StudentTestRuntimeException(i18nUtils.getMessage("error.InvalidMinAnswersCount"));
        if (studentTestList.size() < allAnswersCount)
            throw new StudentTestRuntimeException(i18nUtils.getMessage("error.InvalidAllAnswersCount"));
    }

}
