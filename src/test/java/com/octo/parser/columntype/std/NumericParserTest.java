package com.octo.parser.columntype.std;

import com.octo.exception.FixedFileParserException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumericParserTest {

    private NumericParser numericParser = new NumericParser();

    @Test(expected = FixedFileParserException.class)
    public void testWhenNonNumericIsProvided() {
        numericParser.parse("1234A");
    }

    @Test(expected = FixedFileParserException.class)
    public void testWhenInvalidNumericInput1IsProvided() {
        numericParser.parse(".");
    }


    @Test(expected = FixedFileParserException.class)
    public void testWhenInvalidNumericInput2IsProvided() {
        numericParser.parse("123.");
    }

    @Test
    public void testWhenValidNumericInputsAreProvided() {
        String result = numericParser.parse("12345");
        assertEquals("12345", result);

        result = numericParser.parse("123.45");
        assertEquals("123.45", result);

        result = numericParser.parse(".45789");
        assertEquals(".45789", result);
    }
}