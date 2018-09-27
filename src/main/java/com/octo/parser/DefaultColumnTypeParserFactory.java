package com.octo.parser;

import com.octo.exception.FixedFileFormatCoverterException;
import com.octo.parser.columntype.TypeParser;
import com.octo.parser.columntype.std.DateParser;
import com.octo.parser.columntype.std.NumericParser;
import com.octo.parser.columntype.std.StringParser;
import com.octo.parser.metadata.ColumnType;

import java.util.HashMap;
import java.util.Map;

/**
 * Default singleton factory for type parsers. Factory uses a flyweight pattern so that too
 * many object of  {@link TypeParser} are generated.
 */
public class DefaultColumnTypeParserFactory implements ColumnTypeParserFactory<String> {


    private static DefaultColumnTypeParserFactory instance;
    private Map<ColumnType, TypeParser> typeParsers;


    private DefaultColumnTypeParserFactory() {
        typeParsers = new HashMap<>();
    }

    /**
     * Singleton instance of factory
     * @return
     */
    public static DefaultColumnTypeParserFactory instance() {

        if (instance == null) {
            synchronized (DefaultColumnTypeParserFactory.class) {
                if (instance == null) {
                    instance = new DefaultColumnTypeParserFactory();
                }
            }
        }

        return instance;

    }

    @Override
    public TypeParser<String> getParser(ColumnType columnType) {
        if (!typeParsers.containsKey(columnType)) {
            synchronized (DefaultColumnTypeParserFactory.class) {
                switch (columnType) {
                    case DATE:
                        typeParsers.putIfAbsent(ColumnType.DATE, new DateParser());
                        break;
                    case STRING:
                        typeParsers.putIfAbsent(ColumnType.STRING, new StringParser());
                        break;
                    case NUMERIC:
                        typeParsers.putIfAbsent(ColumnType.NUMERIC, new NumericParser());
                        break;
                    default:
                        throw new FixedFileFormatCoverterException("Type converter not found for type " + columnType);
                }
            }
        }


        return typeParsers.get(columnType);
    }
}
