package com.assignment.fffc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "outputFileName",
        "status",
        "exception"
})
@Getter @Setter @ToString
public class ConvertResponse {

    @JsonProperty("outputFileName")
    @NonNull
    private String outputFileName;

    @JsonProperty("status")
    @NonNull
    private String status;

    @JsonProperty("exception")
    private String exception;

}
