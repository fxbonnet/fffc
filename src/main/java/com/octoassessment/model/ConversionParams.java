package com.octoassessment.model;

public class ConversionParams {
    private String sourcePath;
    private String destinationPath;
    private String metadataPath;

    public ConversionParams(String sourcePath, String destinationPath, String metadataPath) {
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
        this.metadataPath = metadataPath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public String getMetadataPath() {
        return metadataPath;
    }

    public void setMetadataPath(String metadataPath) {
        this.metadataPath = metadataPath;
    }
}
