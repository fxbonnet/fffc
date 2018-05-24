package com.octo.fffc.exceptions;

/**
 * Exception class that is thrown when there is a transformation error.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public class TransformationException extends RuntimeException {
    public TransformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransformationException(String message) {
        super(message,null);
    }
}
