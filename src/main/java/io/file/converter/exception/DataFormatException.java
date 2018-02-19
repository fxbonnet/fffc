package io.file.converter.exception;

public class DataFormatException extends RuntimeException {

    public DataFormatException(String msg, Exception e) {
        super(msg, e);
    }

    public DataFormatException(String msg) {
        super(msg);
    }

}
