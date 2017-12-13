package com.octo.fffc.model.formatter;

import com.octo.fffc.exception.ParseErrorException;
import com.octo.fffc.model.ColumnType;

public class GenericFormatter {

    public String format(ColumnType columnType, String value) throws ParseErrorException{

        switch (columnType) {
            case DATE:
                return new DateFormatter().format(value);
            case STRING:
                return new StringFormatter().format(value);
            case NUMERIC:
                return new NumericFormatter().format(value);
        }

        throw new IllegalArgumentException("Unknown column type: " + columnType);
    }
}
