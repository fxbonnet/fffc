/**
 * Copyright 2018 OCTO Technology.
 */
package com.octo.fixedfileformatconverter.exceptions;

/**
 * Invalid Input Output Format Exception.
 *
 * Exception thrown when there is no converter of a specified pair of input and output formats.
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
