package com.octo.exception;

/**
 * Exception for data parsing errors
 */
public class FixedFileParserException extends FixedFileFormatCoverterException {
    public FixedFileParserException(String message) {
        super(message);
    }
}
