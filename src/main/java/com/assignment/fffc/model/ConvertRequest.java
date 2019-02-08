package com.assignment.fffc.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "inputFileLocation",
        "metadataFileLocation",
        "outputFileName",
        "formatType"
})
@Getter @Setter @ToString
public class ConvertRequest {

    @JsonProperty("inputFileLocation")
    @NonNull
    private String inputFileLocation;

    @JsonProperty("metadataFileLocation")
    @NonNull
    private String metadataFileLocation;

    @JsonProperty("outputFileName")
    @NonNull
    private String outputFileName;

    @JsonProperty("formatType")
    @NonNull
    private String formatType;

}
