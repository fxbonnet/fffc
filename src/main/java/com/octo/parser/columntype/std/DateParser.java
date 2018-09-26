package com.octo.parser.columntype.std;

import com.octo.exception.FixedFileParserException;
import com.octo.parser.columntype.TypeParser;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser implements TypeParser<String> {
    private final SimpleDateFormat FROM_FORMAT = new SimpleDateFormat("yyyy-mm-dd");
    private final SimpleDateFormat TO_FORMAT = new SimpleDateFormat("dd/mm/yyyy");

    @Override
    public String parse(String value) {
        if (StringUtils.isNotBlank(value)) {
            try {
                Date date = FROM_FORMAT.parse(value);
                return TO_FORMAT.format(date);
            } catch (ParseException e) {
                throw new FixedFileParserException("Illegal date format found in file");
            }
        }

        return StringUtils.EMPTY;
    }
}
