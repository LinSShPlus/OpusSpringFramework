package ru.otus.spring.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentTest
 **/
@ToString
@Getter
@Setter
public class StudentTest {

    private int id;
    private String question;
    private short rightAnswer;
    private List<String> answers;

    public StudentTest() {
        answers = new ArrayList<>();
    }

}
