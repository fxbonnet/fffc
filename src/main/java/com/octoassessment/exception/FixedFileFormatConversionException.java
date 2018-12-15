package com.octoassessment.exception;

public class FixedFileFormatConversionException extends Exception{

    public FixedFileFormatConversionException(String message) {
        super(message);
    }

    public FixedFileFormatConversionException(Exception ex) {
        super(ex);
    }
}
