package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Genre
 **/
@Builder
@Data
public class Genre {

    private long id;
    private String brief;
    private String name;

}
