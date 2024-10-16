package ru.otus.spring.domain;

import lombok.*;

/**
 * FoodWithSauce
 **/
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodWithSauce {

    private Food food;
    private Sauce sauce;

}
