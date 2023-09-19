package ru.otus.spring.domain;

import lombok.*;

/**
 * Student
 **/
@Builder
@Data
@RequiredArgsConstructor
public class Student {

    private final String firstName;
    private final String lastName;

}
