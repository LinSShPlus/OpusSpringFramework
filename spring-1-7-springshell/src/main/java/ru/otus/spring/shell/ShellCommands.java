package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.service.ExamService;
import ru.otus.spring.service.StudentTestService;

import java.util.ArrayList;
import java.util.List;

/**
 * ShellCommands
 **/
@RequiredArgsConstructor
@ShellComponent
public class ShellCommands {

    private final ExamService examService;
    private final StudentTestService studentTestService;

    @ShellMethod(value = "Start exam", key = {"s", "start"})
    public void startExam() {
        examService.runExam();
    }

    @ShellMethod(value = "Print student test", key = {"p", "print"})
    public void printTest() {
        List<StudentTest> studentTestList = studentTestService.getTest();
        studentTestService.printTest(studentTestList);
    }

}
