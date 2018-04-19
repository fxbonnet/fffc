/*
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter.exceptions;

/**
 * Invalid Column Format Exception.
 *
 * Exception thrown when a unknown column format is specified.
 *
 * @author Mark Zsilavecz
 */
public class InvalidColumnFormatException extends Exception
{

    /**
     * Constructs an instance of <code>InvalidColumnFormatException</code> with the specified detail message.
     *
     * @param msg the message.
     */
    public InvalidColumnFormatException(String msg)
    {
        super(msg);
    }
}
