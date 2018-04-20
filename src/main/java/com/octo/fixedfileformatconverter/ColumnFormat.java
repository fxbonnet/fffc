/*
 * Copyright 2018 OCTO Technology.
 */
package com.octo.fixedfileformatconverter;

import com.octo.fixedfileformatconverter.exceptions.InvalidColumnFormatException;
import java.util.Locale;

/**
 * Column Format.
 *
 * The column format/data types.
 *
 * @author Mark Zsilavecz
 */
public enum ColumnFormat
{
    /**
     * A column contains a date.
     */
    DATE("date"),
    /**
     * A column contains a numeric value.
     */
    NUMERIC("numeric"),
    /**
     * A column contains a string value.
     */
    STRING("string");

    private final String format;

    /**
     * Private constructor.
     */
    private ColumnFormat(String format)
    {
        this.format = format;
    }

    @Override
    public String toString()
    {
        return format;
    }

    /**
     * Returns the enum constant of {@link ColumnFormat} with the specified name. Case is ignored.
     *
     * @param value the string value.
     *
     * @return the matching {@link ColumnFormat} enum constant.
     *
     * @throws InvalidColumnFormatException if {@link ColumnFormat} enum type has no constant with the specified name.
     */
    public static ColumnFormat of(String value) throws InvalidColumnFormatException
    {
        try
        {
            return valueOf(value.toUpperCase(Locale.getDefault()));
        }
        catch (IllegalArgumentException | NullPointerException e)
        {
            throw new InvalidColumnFormatException(value);
        }
    }
}
