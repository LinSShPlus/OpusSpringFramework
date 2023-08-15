package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private short correctAnswer;
    private List<String> answers;

    public StudentTest() {
        answers = new ArrayList<>();
    }

}
