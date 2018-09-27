package com.octo.parser.columntype.std;

import com.octo.exception.FixedFileParserException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateParserTest {

    private DateParser dateParser = new DateParser();
    ;

    @Test(expected = FixedFileParserException.class)
    public void testWhenInvalidDateFormatIsProvided() {
        dateParser.parse("01-12-2012");
    }

    @Test(expected = FixedFileParserException.class)
    public void testWhenInvalidDateValueIsProvided() {
        dateParser.parse("2012-13-40");
    }

    @Test
    public void testWhenValidDateValueIsProvided() {
        String result = dateParser.parse("2012-12-01");
        assertEquals("01/12/2012", result);
    }

}