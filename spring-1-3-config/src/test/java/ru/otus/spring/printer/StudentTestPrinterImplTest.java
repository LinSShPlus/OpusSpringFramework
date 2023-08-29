package ru.otus.spring.printer;

import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.StudentTest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * StudentTestPrinterImplTest
 **/
class StudentTestPrinterImplTest {

    @Test
    void print() {
        StudentTest studentTest = new StudentTest();
        studentTest.setId(1);
        studentTest.setQuestion("Who has invented the electric bulb?");
        studentTest.setRightAnswer((short) 3);
        studentTest.setAnswers(List.of("Rudolf Diesel", "Benjamin Franklin", "Alexander G. Bell", "Thomas Alva Edison"));

        java.util.logging.Logger logger = Logger.getLogger(StudentTestPrinterImpl.class.getName());
        Level logLevel = logger.getLevel();
        OutputStream logOut = new ByteArrayOutputStream(256);
        Handler handler = new StreamHandler(logOut, new SimpleFormatter());
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);
        try {
            BasePrinter printer = new StudentTestPrinterImpl();
            printer.print(List.of(studentTest));
        } finally {
            logger.removeHandler(handler);
            handler.flush();
            handler.close();
            logger.setLevel(logLevel);
        }
        String logMsg = logOut.toString();
        int contains = logMsg.indexOf(studentTest.toString());
        assertTrue(contains > -1);
    }

}
