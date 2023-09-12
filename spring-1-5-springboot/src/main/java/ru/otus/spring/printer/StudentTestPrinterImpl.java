package ru.otus.spring.printer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.service.OutputService;

import java.util.List;

/**
 * StudentTestPrinterImpl
 **/
@RequiredArgsConstructor
@Component
public class StudentTestPrinterImpl implements BasePrinter {

    private final OutputService outputService;

    public void print(Object object) {
        if (object instanceof List<?>) {
            ((List<?>) object)
                    .stream()
                    .filter(StudentTest.class::isInstance)
                    .map(StudentTest.class::cast)
                    .toList()
                    .forEach(st -> outputService.output(st.toString()));
        }
    }

}
