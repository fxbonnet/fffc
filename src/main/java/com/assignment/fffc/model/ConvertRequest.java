package com.assignment.fffc.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataFilePath",
        "metadataFilePath",
        "outputFileName",
        "formatType"
})
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class ConvertRequest {

    @JsonProperty("dataFilePath")
    @NonNull
    private String inputFileLocation;

    @JsonProperty("metadataFilePath")
    @NonNull
    private String metadataFileLocation;

    @JsonProperty("outputFileName")
    @NonNull
    private String outputFileName;

    @JsonProperty("formatType")
    @NonNull
    private String formatType;

}
