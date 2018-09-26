package com.octo.parser;

import com.octo.parser.columntype.TypeParser;
import com.octo.parser.metadata.ColumnType;

public interface ColumnTypeParserFactory<T> {
    TypeParser<T> getParser(ColumnType columnType);
}
