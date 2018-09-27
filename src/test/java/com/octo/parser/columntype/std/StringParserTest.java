package com.octo.parser.columntype.std;

import com.octo.exception.FixedFileParserException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringParserTest {

    private StringParser stringParser = new StringParser();

    @Test
    public void testWhenInputOnlyHasEmptyStrings() {
        String result = stringParser.parse(null);
        assertEquals(StringUtils.EMPTY, result);

        result = stringParser.parse("");
        assertEquals(StringUtils.EMPTY, result);

        result = stringParser.parse("  ");
        assertEquals(StringUtils.EMPTY, result);
    }


    @Test(expected = FixedFileParserException.class)
    public void testWhenInputHasIllegalCharacter1() {
        stringParser.parse("\r");
    }

    @Test(expected = FixedFileParserException.class)
    public void testWhenInputHasIllegalCharacter2() {
        stringParser.parse("\n");
    }

    @Test(expected = FixedFileParserException.class)
    public void testWhenInputHasMultipleIllegalCharacters() {
        stringParser.parse("\n\r");
    }

    @Test
    public void testWhenStringHasSpecialCharacterAreEscaped() {
        String result = stringParser.parse("ab,cd");
        assertEquals("\"ab,cd\"", result);

        result = stringParser.parse(",");
        assertEquals("\",\"", result);

        result = stringParser.parse("123,");
        assertEquals("\"123,\"", result);

    }

    @Test
    public void testValidStrings() {
        String result = stringParser.parse("abcd");
        assertEquals("abcd", result);

        result = stringParser.parse("abcd$1123");
        assertEquals("abcd$1123", result);
    }

}