package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Book
 **/
@Builder
@Data
public class Book {

    private long id;
    private String brief;
    private String title;
    private String text;
    private long authorId;
    private long genreId;

}
