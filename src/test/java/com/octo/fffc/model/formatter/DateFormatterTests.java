package com.octo.fffc.model.formatter;

import com.octo.fffc.exception.ParseErrorException;
import org.junit.Assert;
import org.junit.Test;

public class DateFormatterTests {

    @Test
    public void testFormatShouldReturnDateFormattedCorrectly() throws ParseErrorException {
        Assert.assertEquals("01/01/1970", new DateFormatter().format("1970-01-01"));
    }

    @Test(expected = ParseErrorException.class)
    public void testFormatShouldThrowExceptionIfDateIsEmpty() throws ParseErrorException {
        new DateFormatter().format("");
    }

    @Test(expected = ParseErrorException.class)
    public void testFormatShouldThrowExceptionIfDateHasMissingFields() throws ParseErrorException {
        new DateFormatter().format("1970-12-");
    }

}
