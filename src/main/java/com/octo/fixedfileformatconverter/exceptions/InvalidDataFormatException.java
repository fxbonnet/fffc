/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter.exceptions;

/**
 *
 * @author Mark Zsilavecz
 */
public class InvalidDataFormatException extends RuntimeException
{

    /**
     * Constructs an instance of <code>InvalidDataFormatException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidDataFormatException(String msg)
    {
        super(msg);
    }
}
