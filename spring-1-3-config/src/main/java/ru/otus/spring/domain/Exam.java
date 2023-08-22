package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Exam
 **/
@ToString
@Getter
@Setter
public class Exam {

    private Student student;
    private int allAnswersCount;
    private int rightAnswersCount;
    private int wrongAnswersCount;
    private boolean isPassed;

}
