/**
 * Copyright 2018 OCTO Technology.
 */
package com.octo.fixedfileformatconverter;

import com.octo.fixedfileformatconverter.exceptions.InvalidDataFormatException;
import com.octo.fixedfileformatconverter.exceptions.InvalidInputOutputFormatException;
import com.octo.fixedfileformatconverter.exceptions.InvalidMetaDataException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Mark Zsilavecz
 */
public class FileConverterTest
{

    private static final String PATH_META = "test_meta.csv";
    private static final String PATH_INPUT = "test_data.txt";
    private static final String PATH_OUTPUT = "test_out.csv";

    public FileConverterTest()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
        try
        {
            Files.delete(Paths.get(PATH_META));
        }
        catch (IOException e)
        {
        }
        try
        {
            Files.delete(Paths.get(PATH_INPUT));
        }
        catch (IOException e)
        {
        }
        try
        {
            Files.delete(Paths.get(PATH_OUTPUT));
        }
        catch (IOException e)
        {
        }
    }

    @Test
    public void testProcess()
    {
        System.out.println("Testing process() ...");

        try (BufferedWriter writer1 = Files.newBufferedWriter(Paths.get(PATH_META),
                                                              StandardCharsets.UTF_8,
                                                              StandardOpenOption.WRITE,
                                                              StandardOpenOption.CREATE))
        {
            writer1.write("Birth date,10,date\r\n"
                          + "First name,15,string\r\n"
                          + "Last name,15,string\r\n"
                          + "Weight,5,numeric");
        }
        catch (IOException e)
        {
        }
        try (BufferedWriter writer2 = Files.newBufferedWriter(Paths.get(PATH_INPUT),
                                                              StandardCharsets.UTF_8,
                                                              StandardOpenOption.WRITE,
                                                              StandardOpenOption.CREATE))
        {
            writer2.write("1970-01-01John           Smith           81.5\r\n"
                          + "1975-01-31Jane           Doe             61.1\r\n"
                          + "1988-11-28Bob            Big            102.4");
        }
        catch (IOException e)
        {
        }

        InputFormat inFormat = InputFormat.FIXED_FORMAT;
        OutputFormat outFormat = OutputFormat.CSV;

        FileConverter converter = new FileConverter();
        try
        {
            converter.process(Paths.get(PATH_META),
                              Paths.get(PATH_INPUT),
                              Paths.get(PATH_OUTPUT),
                              inFormat,
                              outFormat);

            try (BufferedReader reader = Files.newBufferedReader(Paths.get(PATH_OUTPUT),
                                                                 StandardCharsets.UTF_8))
            {
                assertEquals("Birth date,First name,Last name,Weight", reader.readLine());
                assertEquals("01/01/1970,John,Smith,81.5", reader.readLine());
                assertEquals("31/01/1975,Jane,Doe,61.1", reader.readLine());
                assertEquals("28/11/1988,Bob,Big,102.4", reader.readLine());
            }
            catch (IOException e)
            {
                fail("IO Exception should not have been thrown.");
            }
        }
        catch (InvalidInputOutputFormatException | InvalidMetaDataException | InvalidDataFormatException | IOException e)
        {
            e.printStackTrace();
            fail("Error should not have been thrown.");
        }

    }

    @Test
    public void testMain()
    {
        System.out.println("Testing main() ...");

        try
        {
            FileConverter.main(new String[]
            {
            });
            fail("Expected IllegalStateException - no arguments provided.");
        }
        catch (IllegalStateException e)
        {
            assertNotNull(e);
        }

        try
        {
            FileConverter.main(new String[]
            {
                "-m", PATH_META
            });
            fail("Expected IllegalStateException - only meta provided.");
        }
        catch (IllegalStateException e)
        {
            assertNotNull(e);
        }

        try
        {
            FileConverter.main(new String[]
            {
                "-i", PATH_INPUT
            });
            fail("Expected IllegalStateException - only input argument provided.");
        }
        catch (IllegalStateException e)
        {
            assertNotNull(e);
        }

        try
        {
            FileConverter.main(new String[]
            {
                "-o", PATH_OUTPUT
            });
            fail("Expected IllegalStateException - only output argument provided.");
        }
        catch (IllegalStateException e)
        {
            assertNotNull(e);
        }

        try
        {
            FileConverter.main(new String[]
            {
                "-m", PATH_META, "-i", PATH_INPUT
            });
            fail("Expected IllegalStateException - only meta and input arguments provided.");
        }
        catch (IllegalStateException e)
        {
            assertNotNull(e);
        }

        try
        {
            FileConverter.main(new String[]
            {
                "-m", PATH_META, "-o", PATH_OUTPUT
            });
            fail("Expected IllegalStateException - only meta and output arguments provided.");
        }
        catch (IllegalStateException e)
        {
            assertNotNull(e);
        }

        try
        {
            FileConverter.main(new String[]
            {
                "-i", PATH_INPUT, "-o", PATH_OUTPUT
            });
            fail("Expected IllegalStateException - only input and output arguments provided.");
        }
        catch (IllegalStateException e)
        {
            assertNotNull(e);
        }

        try
        {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(PATH_META),
                                                                 StandardCharsets.UTF_8,
                                                                 StandardOpenOption.WRITE,
                                                                 StandardOpenOption.CREATE))
            {
                writer.write("Test,1,string\r\n");
            }
            Files.createFile(Paths.get(PATH_INPUT));

            FileConverter.main(new String[]
            {
                "-m", PATH_META, "-i", PATH_INPUT, "-o", PATH_OUTPUT
            });

        }
        catch (IllegalStateException e)
        {
            fail("Expected no exception - all required args provided.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            fail("IOException should not have been thrown.");
        }
    }

}
