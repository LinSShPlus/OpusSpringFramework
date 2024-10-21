package ru.otus.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * AuthError
 **/
@Builder
@Setter
@Getter
@AllArgsConstructor
public class AuthError {

    @JsonProperty("error")
    private String error;

}
