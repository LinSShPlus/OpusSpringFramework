package ru.otus.spring.reader;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.exception.StudentTestRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * StudentTestReaderImpl
 **/
public class StudentTestReaderImpl implements StudentTestReader {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Setter
    private String fileName;
    @Setter
    private String delimiter;

    @Override
    public List<StudentTest> getStudentTest() {
        List<StudentTest> studentTestList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                StudentTest studentTest = new StudentTest();
                String[] values = line.split(delimiter);

                for (int i = 0; i < values.length; i++) {
                    switch (i) {
                        case 0 -> studentTest.setId(Integer.parseInt(values[i]));
                        case 1 -> studentTest.setQuestion(values[i]);
                        case 2 -> studentTest.setCorrectAnswer(Short.parseShort(values[i]));
                        default -> studentTest.getAnswers().add(values[i]);
                    }
                }
                studentTestList.add(studentTest);
            }
            reader.close();
        } catch (StudentTestRuntimeException e) {
            logger.severe(e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, String.format("Error read file %s", fileName), e);
        }

        return studentTestList;
    }

    private InputStream getInputStream() {
        if (StringUtils.isEmpty(fileName))
            throw new StudentTestRuntimeException("Filename of student test not defined");
        InputStream stream = getClass().getResourceAsStream(fileName);
        if (stream == null)
            throw new StudentTestRuntimeException(String.format("Error read file %s", fileName));
        return stream;
    }

}
