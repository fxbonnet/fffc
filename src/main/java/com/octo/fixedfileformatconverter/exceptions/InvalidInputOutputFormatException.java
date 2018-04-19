/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter.exceptions;

/**
 *
 * @author Mark Zsilavecz
 */
public class InvalidInputOutputFormatException extends RuntimeException
{

    /**
     * Constructs an instance of <code>InvalidInputOutputFormatException</code> with the specified detail message.
     *
     * @param msg the message.
     */
    public InvalidInputOutputFormatException(String msg)
    {
        super(msg);
    }
}
