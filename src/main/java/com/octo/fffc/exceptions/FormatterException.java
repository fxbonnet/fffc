package com.octo.fffc.exceptions;

/**
 * Exception class that is thrown on formatter error.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormatterException extends RuntimeException {
    public FormatterException(String message, Throwable ex) {
        super(message, ex);
    }

    public FormatterException(String message) {
        this(message, null);
    }
}