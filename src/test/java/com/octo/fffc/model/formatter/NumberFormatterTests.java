package com.octo.fffc.model.formatter;

import com.octo.fffc.exception.ParseErrorException;
import org.junit.Assert;
import org.junit.Test;

public class NumberFormatterTests  {

    @Test
    public void format() throws ParseErrorException {
        Assert.assertEquals("10.7", new NumericFormatter().format("10.7"));
    }

    @Test
    public void formatTrimNumber() throws ParseErrorException {
        Assert.assertEquals("10.7", new NumericFormatter().format(" 10.7 "));
    }

    @Test(expected = ParseErrorException.class)
    public void testFormatShouldThrowExceptionIfNumberIsEmptyString() throws ParseErrorException {
        new NumericFormatter().format(" ");
    }

    @Test(expected = ParseErrorException.class)
    public void testFormatShouldThrowExceptionIfInputIsNotANumber() throws ParseErrorException {
        new NumericFormatter().format("A.b");
    }
}
