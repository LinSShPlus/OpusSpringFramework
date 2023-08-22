package ru.otus.spring.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * StudentTestConfig
 **/
@Getter
@Configuration
public class StudentTestConfig {

    private final String fileName;
    private final String delimiter;
    private final int allAnswersCount;
    private final int minAnswersCount;

    public StudentTestConfig(@Value("${studentTest.fileName}") String fileName,
                             @Value("${studentTest.delimiter}") String delimiter,
                             @Value("${studentTest.allAnswersCount}") int allAnswersCount,
                             @Value("${studentTest.minAnswersCount}") int minAnswersCount) {
        this.fileName = fileName;
        this.delimiter = delimiter;
        this.allAnswersCount = allAnswersCount;
        this.minAnswersCount = minAnswersCount;
    }

}
