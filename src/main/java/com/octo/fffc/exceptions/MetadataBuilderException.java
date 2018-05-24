package com.octo.fffc.exceptions;

/**
 * Exception class that is thrown when there are errors in Metadata.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public class MetadataBuilderException extends RuntimeException {
    public MetadataBuilderException(String message) {
        super(message);
    }
}
