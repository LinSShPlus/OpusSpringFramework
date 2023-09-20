package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.printer.BasePrinter;
import ru.otus.spring.reader.BaseReader;
import ru.otus.spring.utils.I18nUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * StudentTestServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class StudentTestServiceImpl implements StudentTestService {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Random random = new Random();
    private final BaseReader<List<String[]>> dataReader;
    private final BaseReader<String> inputReader;
    @Qualifier("studentTestPrinter")
    private final BasePrinter printer;
    private final OutputService outputService;
    private final I18nUtils i18nUtils;

    @Override
    public List<StudentTest> getTest() {
        List<StudentTest> studentTestList = new ArrayList<>();

        dataReader.read().forEach(values -> {
            StudentTest studentTest = new StudentTest();
            for (int i = 0; i < values.length; i++) {
                switch (i) {
                    case 0 -> studentTest.setId(Integer.parseInt(values[i]));
                    case 1 -> studentTest.setQuestion(values[i]);
                    case 2 -> studentTest.setRightAnswer(Short.parseShort(values[i]));
                    default -> studentTest.getAnswers().add(values[i]);
                }
            }
            studentTestList.add(studentTest);
        });

        return studentTestList;
    }

    @Override
    public List<StudentTest> getTest(List<StudentTest> list, int count) {
        List<StudentTest> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            IntStream stream = random.ints(0, list.size()).distinct().limit(count);
            stream.forEach(n -> result.add(list.get(n)));
        }
        return result;
    }

    @Override
    public void printTest(List<StudentTest> list) {
        if (!CollectionUtils.isEmpty(list)) {
            logger.info(() -> i18nUtils.getMessage("output.printTest"));
            printer.print(list);
        } else {
            logger.severe(() -> i18nUtils.getMessage("error.testNotFound"));
        }
    }

    @Override
    public short getAnswer(StudentTest studentTest) {
        AtomicInteger i = new AtomicInteger();
        StringBuilder stringBuilder = new StringBuilder();
        studentTest.getAnswers().forEach(a -> stringBuilder.append(String.format("%n%d. %s", i.incrementAndGet(), a)));
        outputService.output(studentTest.getQuestion() + stringBuilder);
        return getValidAnswer(i.get());
    }

    private short getValidAnswer(int answerCount) {
        short answer;
        while (true) {
            try {
                answer = Short.parseShort(inputReader.read());
                if (answer <= 0 || answer > answerCount)
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                logger.severe(i18nUtils.getMessage("error.invalidAnswer"));
            }
        }
        return answer;
    }

}