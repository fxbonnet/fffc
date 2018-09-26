package com.octo.parser.columntype.std;

import com.octo.exception.FixedFileParserException;
import com.octo.parser.columntype.TypeParser;
import org.apache.commons.lang3.StringUtils;

public class NumericParser implements TypeParser<String> {

    private final String NUMERIC_PATTERN = "^-?[0-9].+";

    @Override
    public String parse(String value) {

        if (StringUtils.isBlank(value)) {
            return StringUtils.EMPTY;
        }

        value = value.trim();

        if (!value.matches(NUMERIC_PATTERN)) {
            throw new FixedFileParserException("Illegal data present for numeric column");
        }

        return value;
    }
}
