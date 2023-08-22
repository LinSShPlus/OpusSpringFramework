package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.printer.Printer;
import ru.otus.spring.reader.StudentTestReader;

import java.util.List;
import java.util.logging.Logger;

/**
 * StudentTestServiceImpl
 **/
@RequiredArgsConstructor
public class StudentTestServiceImpl implements StudentTestService {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final StudentTestReader reader;
    private final Printer printer;

    @Override
    public void printTest() {
        List<StudentTest> studentTestList = reader.getStudentTest();

        if (!CollectionUtils.isEmpty(studentTestList)) {
            logger.info("Print student test");
            printer.print(studentTestList);
        } else {
            logger.info("Student test not found");
        }
    }

}
