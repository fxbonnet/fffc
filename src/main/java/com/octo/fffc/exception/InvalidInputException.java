package com.octo.fffc.exception;

/**
 * A wrapper exception for indicating that the input is invalid
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String msg, Exception ex) {
        super(msg, ex);
    }

    public InvalidInputException(String msg) {
        super(msg);
    }
}