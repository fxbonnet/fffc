package com.octo.fffc.exceptions;

import java.io.IOException;

/**
 * Exception that is thrown whenever there is a error while reading.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public class ReaderException extends IOException {

    public ReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
