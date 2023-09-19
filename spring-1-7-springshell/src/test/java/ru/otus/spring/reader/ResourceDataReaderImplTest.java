package ru.otus.spring.reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * ResourceDataReaderImplTest
 **/
@DisplayName("Класс ResourceDataReaderImpl")
@SpringBootTest
class ResourceDataReaderImplTest {

    @Autowired
    private ResourceDataReaderImpl reader;

    @DisplayName("должен прочитать вопросы для тестирования студентов из CSV-файла")
    @Test
    void getStudentTest() {
        List<String[]> dataList = reader.read();

        assertFalse(CollectionUtils.isEmpty(dataList));
    }

}
