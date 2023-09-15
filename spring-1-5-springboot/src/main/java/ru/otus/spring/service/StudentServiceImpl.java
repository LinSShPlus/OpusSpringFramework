package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.reader.BaseReader;
import ru.otus.spring.utils.I18nUtils;

import java.util.logging.Logger;

/**
 * StudentServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final BaseReader<String> inputReader;
    private final I18nUtils i18nUtils;

    @Override
    public Student getStudent() {
        logger.info(() -> i18nUtils.getMessage("input.firstName"));
        String firstName = inputReader.read();
        logger.info(() -> i18nUtils.getMessage("input.lastName"));
        String lastName = inputReader.read();
        return Student
                .builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

}
