package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.reader.BaseReader;

import java.util.logging.Logger;

/**
 * StudentServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final BaseReader<String> inputReader;

    @Override
    public Student getStudent() {
        logger.info("Enter the first name of the student");
        String firstName = inputReader.read();
        logger.info("Enter the last name of the student");
        String lastName = inputReader.read();
        return Student
                .builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

}
