package com.an.util;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class DateFormatterTest  {

    @Test
    public void testDateFormatterSuccess() throws  Exception
    {

        String sourceDate = "1970-01-01";

        System.out.println(DateFormatter.formatDate(sourceDate));

        assertEquals("01/01/1970",DateFormatter.formatDate(sourceDate));

        sourceDate = "1975-01-31";

        System.out.println(DateFormatter.formatDate(sourceDate));

        assertEquals("31/01/1975",DateFormatter.formatDate(sourceDate));

        sourceDate = "1988-11-28";

        System.out.println(DateFormatter.formatDate(sourceDate));

        assertEquals("28/11/1988",DateFormatter.formatDate(sourceDate));

    }


    @Test(expected = ParseException.class)
    public void testDateFormatterFailureIncompleteDate() throws  Exception
    {

        String sourceDate = "-01-01";
        DateFormatter.formatDate(sourceDate);

    }

    @Test
    public void testDateFormatterNullDate() throws  Exception
    {

        String sourceDate = null;
        assertEquals(null,DateFormatter.formatDate(sourceDate));

    }

    @Test
    public void testDateFormatterEmptyDate() throws  Exception
    {

        String sourceDate = "";
        assertEquals("",DateFormatter.formatDate(sourceDate));

    }
}
