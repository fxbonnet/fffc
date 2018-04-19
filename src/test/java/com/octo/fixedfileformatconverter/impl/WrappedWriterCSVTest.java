/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Mark Zsilavecz
 */
public class WrappedWriterCSVTest
{

    private static final Path TEST_FILE = Paths.get("test_out.csv");

    public WrappedWriterCSVTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
        try
        {
            Files.delete(TEST_FILE);
        }
        catch (IOException e)
        {
            System.out.println("Unable to delete test file.");
            e.printStackTrace();
        }
    }

    @Test
    public void testWrite() throws Exception
    {
        System.out.println("Testing write() ...");

        String[] line1 = new String[]
        {
            "28/11/1988", "Bob", "Big", "102.4"
        };
        String[] line2 = new String[]
        {
            "31/01/1975", "Jan%e", "D\"o'e", "-61.1"
        };

        try (WrappedWriterCSV writer = new WrappedWriterCSV(TEST_FILE))
        {
            writer.write(line1);
            writer.write(line2);
        }
        catch (IOException e)
        {
            fail("Unexpected IO exception: " + e.getMessage());
        }

        String expected1 = "28/11/1988,Bob,Big,102.4";
        String expected2 = "31/01/1975,Jan%e,\"D\\\"o'e\",-61.1";
        String read1 = null;
        String read2 = null;
        try (BufferedReader reader = Files.newBufferedReader(TEST_FILE, StandardCharsets.UTF_8))
        {
            read1 = reader.readLine();
            read2 = reader.readLine();
        }
        catch (IOException e)
        {
            fail("Unexpected IO exception: " + e.getMessage());
        }

        assertEquals(expected1, read1);
        assertEquals(expected2, read2);
    }

}
