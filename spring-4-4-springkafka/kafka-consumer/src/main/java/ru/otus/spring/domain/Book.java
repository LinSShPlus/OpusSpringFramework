package ru.otus.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Book
 **/
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    private Long id;

    private String brief;

    private String title;

    private String text;

    private Author author;

    private Genre genre;

}
