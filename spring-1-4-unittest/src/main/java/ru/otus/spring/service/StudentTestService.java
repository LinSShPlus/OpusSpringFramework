package ru.otus.spring.service;

import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.StudentTest;

import java.util.List;

/**
 * StudentTestService
 **/
public interface StudentTestService {

    List<StudentTest> getTest();

    List<StudentTest> getTest(List<StudentTest> list, int count);

    void printTest(List<StudentTest> list);

    short getAnswer(StudentTest studentTest);

}
