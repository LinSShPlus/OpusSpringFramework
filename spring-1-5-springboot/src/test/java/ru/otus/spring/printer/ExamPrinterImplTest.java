package ru.otus.spring.printer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Exam;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.OutputService;
import ru.otus.spring.utils.I18nUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * ExamPrinterImplTest
 **/
@DisplayName("Класс ExamPrinterImpl")
@SpringBootTest
class ExamPrinterImplTest {

    @MockBean
    private OutputService outputService;
    @Qualifier("examPrinter")
    @Autowired
    private BasePrinter printer;
    @Autowired
    private I18nUtils i18nUtils;

    @DisplayName("должен вывести результат экзамена для студента")
    @Test
    void print() {
        Exam exam = createExam();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        printer.print(exam);

        verify(outputService).output(captor.capture());
        assertEquals(captor.getValue(), getExamReport(exam));
    }

    private Exam createExam() {
        Exam exam = new Exam();
        exam.setStudent(new Student("Ivan", "Petrov"));
        exam.setAllAnswersCount(5);
        exam.setRightAnswersCount(4);
        exam.setWrongAnswersCount(1);
        exam.setPassed(true);
        return exam;
    }

    private String getExamReport(Exam exam) {
        return i18nUtils.getMessage("exam.report",
                exam.getStudent().getFirstName(),
                exam.getStudent().getLastName(),
                exam.getAllAnswersCount(),
                exam.getRightAnswersCount(),
                exam.getWrongAnswersCount(),
                i18nUtils.getMessage(String.valueOf(exam.isPassed())));
    }

}