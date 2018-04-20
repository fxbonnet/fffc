/*
 * Copyright 2018 OCTO Technology.
 */
package com.octo.fixedfileformatconverter.exceptions;

/**
 * Invalid Meta Data Exception.
 *
 * Exception thrown when the meta data is not valid.
 *
 * @author Mark Zsilavecz
 */
public class InvalidMetaDataException extends Exception
{

    public InvalidMetaDataException(String message)
    {
        super(message);
    }

}
