/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter.impl;

import com.octo.fixedfileformatconverter.ColumnFormat;
import com.octo.fixedfileformatconverter.exceptions.InvalidDataFormatException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Mark Zsilavecz
 */
public class DefaultConverterTest
{

    public DefaultConverterTest()
    {
    }

    /**
     * Test of convert method, of class DefaultConverter.
     */
    @Test
    public void testConvert()
    {
        System.out.println("Testing convert() ...");

        String rawGood = "1970-01-01John           Smith           81.5";
        String rawBad1 = "1970-01-1 John           Smith           81.5";
        String rawBad2 = "1970-01-01John           Smith           c1.5";
        String rawBad3 = "1970-01-01J Smith         81.5";

        String[] expResult = new String[]
        {
            "01/01/1970", "John", "Smith", "81.5"
        };

        List<DefaultColumnMetaData> columns = Arrays.asList(new DefaultColumnMetaData[]
        {
            DefaultColumnMetaData.from("Birth date", 10, ColumnFormat.DATE),
            DefaultColumnMetaData.from("First name", 15, ColumnFormat.STRING),
            DefaultColumnMetaData.from("Last name", 15, ColumnFormat.STRING),
            DefaultColumnMetaData.from("Weight", 5, ColumnFormat.NUMERIC)
        });

        DefaultConverter instance = new DefaultConverter();
        try
        {
            String[] result = instance.convert(rawGood, columns);
            assertArrayEquals(expResult, result);
        }
        catch (InvalidDataFormatException e)
        {
            System.out.println("Error: " + e.getMessage());
            fail("Expected no exception.");
        }

        try
        {
            String[] resultBad = instance.convert(rawBad1, columns);
            fail("Expecting InvalidDataFormatException");
        }
        catch (InvalidDataFormatException e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        try
        {
            String[] resultBad = instance.convert(rawBad2, columns);
            fail("Expecting InvalidDataFormatException");
        }
        catch (InvalidDataFormatException e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        try
        {
            String[] resultBad = instance.convert(rawBad3, columns);
            fail("Expecting InvalidDataFormatException");
        }
        catch (InvalidDataFormatException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
