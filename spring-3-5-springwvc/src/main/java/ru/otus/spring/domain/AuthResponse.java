package ru.otus.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * AuthResponse
 **/
@Builder
@Setter
@Getter
@AllArgsConstructor
public class AuthResponse {

    public static final String TOKEN_TYPE_BEARER = "Bearer";

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("token")
    private String token;

}
