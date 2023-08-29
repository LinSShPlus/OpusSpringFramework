package ru.otus.spring.reader;

import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.config.StudentTestConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * ResourceDataReaderImplTest
 **/
class ResourceDataReaderImplTest {

    private final StudentTestConfig studentTestConfig;

    ResourceDataReaderImplTest() {
        studentTestConfig = new StudentTestConfig(
                "/StudentTest.csv",
                "\\|",
                5,
                4
        );
    }

    @Test
    void getStudentTest() {
        ResourceDataReaderImpl reader = new ResourceDataReaderImpl(studentTestConfig);
        List<String[]> dataList = reader.read();

        assertFalse(CollectionUtils.isEmpty(dataList));
    }

}
