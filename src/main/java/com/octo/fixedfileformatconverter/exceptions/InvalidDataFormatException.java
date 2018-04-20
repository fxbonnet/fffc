/**
 * Copyright 2018 OCTO Technology.
 */
package com.octo.fixedfileformatconverter.exceptions;

/**
 * Invalid Data Format Exception.
 *
 * Exception thrown when the input data is malformed.
 *
 * @author Mark Zsilavecz
 */
public class InvalidDataFormatException extends Exception
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
