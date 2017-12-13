package com.octo.fffc.model.formatter;

import com.octo.fffc.exception.ParseErrorException;
import org.junit.Assert;
import org.junit.Test;

public class StringFormatterTests {

    @Test
    public void testFormatShouldTrimBlankSpaces() throws ParseErrorException {
        Assert.assertEquals("John", new StringFormatter().format("John           "));
    }

    @Test
    public void testFormatShouldEscapeStringWithCommas() throws ParseErrorException {
        Assert.assertEquals("\"John, Doe\"", new StringFormatter().format("John, Doe"));
    }

    @Test
    public void testFormat() throws ParseErrorException {
        Assert.assertEquals("John Paul", new StringFormatter().format("John Paul"));
    }

    @Test(expected = ParseErrorException.class)
    public void testFormatShouldThrowExceptionIfStringIsEmpty() throws ParseErrorException {
        new StringFormatter().format(" ");
    }
}
