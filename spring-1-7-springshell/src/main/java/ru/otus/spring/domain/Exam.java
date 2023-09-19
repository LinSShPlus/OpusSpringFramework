package ru.otus.spring.domain;

import lombok.Data;

/**
 * Exam
 **/
@Data
public class Exam {

    private Student student;
    private int allAnswersCount;
    private int rightAnswersCount;
    private int wrongAnswersCount;
    private boolean isPassed;

}
