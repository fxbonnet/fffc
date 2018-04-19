/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter;

import com.octo.fixedfileformatconverter.exceptions.InvalidDataFormatException;
import java.util.List;

/**
 * Converter.
 *
 * A {@link Converter} converts a line from an input file to an array of strings, which can be written to a file or
 * other data store.
 *
 * @author Mark Zsilavecz
 *
 * @param <T> the type of meta data the converter uses.
 * @param <U> the type of output the converter produces, per line of input.
 */
public interface Converter<T extends ColumnMetaData, U>
{

    /**
     * Converts a line from the input file.
     *
     * @param raw the raw line before conversion.
     * @param columns the column meta data.
     *
     * @return a string array that can be written to an output file.
     *
     * @throws InvalidDataFormatException if the format of the raw line is invalid according to the meta data.
     */
    public U convert(String raw, List<T> columns) throws InvalidDataFormatException;
}
