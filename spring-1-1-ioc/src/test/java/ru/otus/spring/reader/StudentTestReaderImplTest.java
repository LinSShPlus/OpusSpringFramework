package ru.otus.spring.reader;

import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.domain.StudentTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * StudentTestReaderImplTest
 **/
class StudentTestReaderImplTest {

    @Test
    void getStudentTest() {
        StudentTestReaderImpl reader = new StudentTestReaderImpl();
        reader.setFileName("/StudentTest.csv");
        reader.setDelimiter("\\|");
        List<StudentTest> studentTestList = reader.getStudentTest();

        assertFalse(CollectionUtils.isEmpty(studentTestList));
    }
}
