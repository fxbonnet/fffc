package com.octo.config;

import com.octo.coverter.FileFormat;
import com.octo.exception.FixedFileFormatCoverterException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgumentParserTest {

    @Test
    public void testNotEnoughArguments() {
        String[] args = {"-iFile=<input file>", "-iFileMetaData=<metadata file>", "-oFile=<output file>", "oFileFormat=<>"};

        boolean result = new ArgumentParser().parseArgs(args);

        assertFalse(result);
    }

    @Test
    public void testAllArumentsArePresent() {
        String[] args = {"-iFile=input.txt", "-iFileMetaData=metadata.txt", "-oFile=results.txt", "-oFileFormat=csv"};
        ArgumentParser argumentParser = new ArgumentParser();
        boolean result = argumentParser.parseArgs(args);

        assertTrue(result);
        assertEquals("input.txt", argumentParser.getInputFileName());
        assertEquals("metadata.txt", argumentParser.getInputFormatFileName());
        assertEquals(FileFormat.CSV, argumentParser.getOutputFileFormat());
        assertEquals("results.txt", argumentParser.getOutputFileName());
    }


    @Test(expected = FixedFileFormatCoverterException.class)
    public void testUnsupportedOutfileFormat() {
        String[] args = {"-iFile=<input file>", "-iFileMetaData=<metadata file>", "-oFile=<output file>", "-oFileFormat=xls"};
        new ArgumentParser().parseArgs(args);
    }

}