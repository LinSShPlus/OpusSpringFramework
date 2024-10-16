package ru.otus.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Order
 **/
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @JsonProperty("foodBrief")
    private String foodBrief;

    @JsonProperty("sauceBrief")
    private String sauceBrief;

    @JsonProperty("isRandomSauce")
    private boolean isRandomSauce;

}
