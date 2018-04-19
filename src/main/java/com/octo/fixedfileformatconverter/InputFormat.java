/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter;

/**
 * Input format.
 *
 * Set of input formats the converter can handle.
 *
 * @author Mark Zsilavecz
 */
public enum InputFormat
{
    FIXED_FORMAT;

    /**
     * Returns the {@link InputFormat} enum constant for the given value. If the value does not match any constant, the
     * default <code>FIXED_FORMAT</code> is returned. An exact match is required.
     *
     * @param value the string value.
     *
     * @return the matching {@link InputFormat} enum constant.
     */
    public static InputFormat of(String value)
    {
        try
        {
            return InputFormat.valueOf(value);
        }
        catch (NullPointerException | IllegalArgumentException e)
        {
            return FIXED_FORMAT;
        }
    }
}
