package ru.otus.spring.printer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Exam;
import ru.otus.spring.service.OutputService;
import ru.otus.spring.utils.I18nUtils;

/**
 * ExamPrinterImpl
 **/
@Qualifier("examPrinter")
@RequiredArgsConstructor
@Component
public class ExamPrinterImpl implements BasePrinter {

    private final OutputService outputService;
    private final I18nUtils i18nUtils;

    public void print(Object object) {
        if (object instanceof Exam exam) {
            String report = i18nUtils.getMessage("exam.report",
                    exam.getStudent().getFirstName(),
                    exam.getStudent().getLastName(),
                    exam.getAllAnswersCount(),
                    exam.getRightAnswersCount(),
                    exam.getWrongAnswersCount(),
                    i18nUtils.getMessage(String.valueOf(exam.isPassed())));
            outputService.output(report);
        }
    }

}
