package com.octo.parser.columntype.std;

import com.octo.exception.FixedFileParserException;
import com.octo.parser.columntype.TypeParser;
import org.apache.commons.lang3.StringUtils;

public class StringParser implements TypeParser<String> {

    private final char[] ILLEGAL_CHARS = {'\n', '\r'};
    private final char SEPARATOR_CHAR = ',';
    private final char WRAPPER_CHAR = '\"';

    @Override
    public String parse(String value) {

        if (StringUtils.isEmpty(value)) {
            return StringUtils.EMPTY;
        }

        if (StringUtils.containsOnly(value, ILLEGAL_CHARS)) {
            throw new FixedFileParserException("Illegal escape sequence character found in text");
        }

        value = value.trim();

        return StringUtils.containsAny(value, SEPARATOR_CHAR) ? StringUtils.wrap(value, WRAPPER_CHAR) : value;
    }
}
