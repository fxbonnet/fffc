/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter;

/**
 * Output format.
 *
 * Set of output formats the converter can handle.
 *
 * @author Mark Zsilavecz
 */
public enum OutputFormat
{
    CSV;

    /**
     * Returns the {@link OutputFormat} enum constant for the given value. If the value does not match any constant, the
     * default <code>CSV</code> is returned. An exact match is required.
     *
     * @param value the string value.
     *
     * @return the matching {@link OutputFormatF} enum constant.
     */
    public static OutputFormat of(String value)
    {
        try
        {
            return OutputFormat.valueOf(value);
        }
        catch (NullPointerException | IllegalArgumentException e)
        {
            return CSV;
        }
    }
}
