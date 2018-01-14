package com.truform.fffc.exceptions;

public class MetadataException extends RuntimeException {
    public MetadataException(String message) {
        super(message);
    }

    public MetadataException(String message, Exception exception) {
        super(message, exception);
    }
}