package com.github.mpawlucz.octo.fffc.domain.parser;

import com.github.mpawlucz.octo.fffc.domain.AbstractColumnParser;
import lombok.Builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateColumnParser extends AbstractColumnParser {

    private static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    static {
        INPUT_DATE_FORMAT.setLenient(false);
    }

    public static final String TYPE_NAME = "date";

    @Builder
    public DateColumnParser(String name, Integer length) {
        super(name, length);
    }

    @Override
    public Object parse(String raw) {
        final String trimmed = raw.trim();
        try {
            final Date parsed = INPUT_DATE_FORMAT.parse(trimmed);
            return OUTPUT_DATE_FORMAT.format(parsed);
        } catch (ParseException e) {
            throw new RuntimeException("cannot parse date: " + trimmed);
        }
    }

}
