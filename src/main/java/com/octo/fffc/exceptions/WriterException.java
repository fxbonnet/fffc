package com.octo.fffc.exceptions;

import java.io.IOException;

/**
 * Exception class that is thrown when there is an error while writing the data.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public class WriterException extends IOException {

    public WriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
