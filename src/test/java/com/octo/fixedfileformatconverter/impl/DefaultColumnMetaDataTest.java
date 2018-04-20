/**
 * Copyright 2018 OCTO Technology.
 */
package com.octo.fixedfileformatconverter.impl;

import com.octo.fixedfileformatconverter.ColumnFormat;
import com.octo.fixedfileformatconverter.exceptions.InvalidMetaDataException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Mark Zsilavecz
 */
public class DefaultColumnMetaDataTest
{

    private static final DefaultColumnMetaData TEST_COLUMN = DefaultColumnMetaData.from("test_value", 10, ColumnFormat.STRING);

    public DefaultColumnMetaDataTest()
    {
    }

    @Test
    public void testOf_3args()
    {
        System.out.println("Testing of(String, int, ColumnFormat)");
        DefaultColumnMetaData column = DefaultColumnMetaData.from("test_value", 10, ColumnFormat.STRING);
        assertEquals(TEST_COLUMN, column);
    }

    @Test
    public void testOf_StringArr()
    {
        System.out.println("Testing of(String[])");
        String[] values = new String[]
        {
            "test_value", "10", "string"
        };
        try
        {
            DefaultColumnMetaData column = DefaultColumnMetaData.from(values);
            assertEquals(TEST_COLUMN, column);
        }
        catch (InvalidMetaDataException e)
        {
            fail("Did not expect exception.");
        }
    }

    @Test
    public void testGetName()
    {
        System.out.println("Testing getName() ...");
        assertEquals("test_value", TEST_COLUMN.getName());
    }

    @Test
    public void testGetLength()
    {
        System.out.println("Testing getLength() ...");
        assertEquals(10, TEST_COLUMN.getLength());
    }

    @Test
    public void testGetFormat()
    {
        System.out.println("Testing getFormat() ...");
        assertEquals(ColumnFormat.STRING, TEST_COLUMN.getFormat());
    }

    /**
     * Test of equals method, of class ColumnMetaData.
     */
    @Test
    public void testEquals()
    {
        System.out.println("Testing equals()");
        DefaultColumnMetaData column1 = DefaultColumnMetaData.from("name", 99, ColumnFormat.NUMERIC);
        String[] values = new String[]
        {
            "name", "99", "numEric"
        };
        try
        {
            DefaultColumnMetaData column2 = DefaultColumnMetaData.from(values);
            assertEquals(column1, column2);
        }
        catch (InvalidMetaDataException e)
        {
            fail("Did not expect exception.");
        }
    }

}
