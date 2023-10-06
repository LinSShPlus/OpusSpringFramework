package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Author
 **/
@Builder
@Data
public class Author {

    private final long id;
    private final String brief;
    private final String lastName;
    private final String firstName;

}
