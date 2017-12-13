package com.octo.fffc.model.formatter;

import com.octo.fffc.exception.ParseErrorException;
import com.octo.fffc.model.ColumnType;
import org.junit.Assert;
import org.junit.Test;

public class GenericFormatterTests {

    @Test
    public void testFormatForDateColumnType() throws ParseErrorException {
        Assert.assertEquals("01/01/1970", new GenericFormatter().format(ColumnType.DATE, "1970-01-01"));
    }

    @Test
    public void testFormatForStringColumnType() throws ParseErrorException {
        Assert.assertEquals("Smith", new GenericFormatter().format(ColumnType.STRING, "Smith           "));
    }

    @Test
    public void testFormatForNumericColumnType() throws ParseErrorException {
        Assert.assertEquals("102.4", new GenericFormatter().format(ColumnType.NUMERIC, "102.4"));
    }
}
