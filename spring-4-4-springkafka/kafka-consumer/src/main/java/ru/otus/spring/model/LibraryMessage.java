package ru.otus.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * LibraryMessage
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@SuppressWarnings({"all"})
public class LibraryMessage {

    @JsonProperty("objectType")
    private String objectType;

    @JsonProperty("object")
    private JsonNode object;

    @JsonProperty("comment")
    private String comment;

}
