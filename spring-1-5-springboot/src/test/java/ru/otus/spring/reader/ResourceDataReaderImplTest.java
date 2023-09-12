package ru.otus.spring.reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.config.StudentTestConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * ResourceDataReaderImplTest
 **/
@DisplayName("Класс ResourceDataReaderImpl")
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

    @DisplayName("должен прочитать вопросы для тестирования студентов из CSV-файла")
    @Test
    void getStudentTest() {
        ResourceDataReaderImpl reader = new ResourceDataReaderImpl(studentTestConfig);
        List<String[]> dataList = reader.read();

        assertFalse(CollectionUtils.isEmpty(dataList));
    }

}
