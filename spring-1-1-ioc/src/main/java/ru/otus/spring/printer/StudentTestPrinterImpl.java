package ru.otus.spring.printer;

import ru.otus.spring.domain.StudentTest;

import java.util.List;
import java.util.logging.Logger;

/**
 * StudentTestPrinterImpl
 **/
public class StudentTestPrinterImpl implements Printer {

    private final Logger logger = Logger.getLogger(getClass().getName());

    public void print(Object object) {
        if (object instanceof List<?>) {
            ((List<?>) object)
                    .stream()
                    .filter(StudentTest.class::isInstance)
                    .map(StudentTest.class::cast)
                    .toList()
                    .forEach(st -> logger.info(st.toString()));
        }
    }

}
