package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Student
 **/
@ToString
@Builder
@Getter
@Setter
public class Student {

    private String firstName;
    private String lastName;

}
