package com.octo.parser;

import com.octo.parser.columntype.TypeParser;
import com.octo.parser.metadata.ColumnType;

/**
 * A factory for {@link TypeParser}
 *
 * @param <T>
 */
public interface ColumnTypeParserFactory<T> {
    /**
     * Returns a parser for the {@link ColumnType}
     *
     * @param columnType
     * @return
     */
    TypeParser<T> getParser(ColumnType columnType);
}
