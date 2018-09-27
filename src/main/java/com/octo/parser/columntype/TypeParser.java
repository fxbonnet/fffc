package com.octo.parser.columntype;

/**
 * Base interface for all data parsers
 *
 * @param <T> return type of the parser
 */
public interface TypeParser<T> {

    /**
     * Parse the input string to a type which the {@link com.octo.coverter.FileConverter} understands.
     *
     * @param value
     * @return
     */
    T parse(String value);
}
