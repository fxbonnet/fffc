package com.octo.coverter.csv;

import com.octo.config.AppConfig;
import com.octo.coverter.FileConverter;
import com.octo.coverter.FileFormat;
import com.octo.exception.FixedFileFormatCoverterException;
import com.octo.parser.DefaultColumnTypeParserFactory;
import com.octo.parser.metadata.ColumnData;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class CSVFileConverterTest {

    private String outFile = System.getProperty("user.dir") + "/src/test/resources/output.txt";

    @After
    public void cleanup() {
        File file = new File(outFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test(expected = FixedFileFormatCoverterException.class)
    public void testInputFileDoesnotExist() {

        String inputFile = System.getProperty("user.dir") + "/src/test/resources/unknown.txt";

        AppConfig appConfig = new AppConfig(inputFile, outFile, FileFormat.CSV, Arrays.asList(
                new ColumnData(new String[]{"column-1", "20", "string"}),
                new ColumnData(new String[]{"column-2", "10", "date"}),
                new ColumnData(new String[]{"column-3", "5", "numeric"})));

        FileConverter fileConverter = new CSVFileConverter(appConfig, DefaultColumnTypeParserFactory.instance());

        fileConverter.convert();
    }

    @Test(expected = FixedFileFormatCoverterException.class)
    public void testParsingErrorInputFile() {

        String inputFile = System.getProperty("user.dir") + "/src/test/resources/invalid-input.txt";
        String outFile = System.getProperty("user.dir") + "/src/test/resources/output.txt";
        AppConfig appConfig = new AppConfig(inputFile, outFile, FileFormat.CSV, Arrays.asList(
                new ColumnData(new String[]{"column-1", "20", "string"}),
                new ColumnData(new String[]{"column-2", "10", "date"}),
                new ColumnData(new String[]{"column-3", "5", "numeric"})));

        FileConverter fileConverter = new CSVFileConverter(appConfig, DefaultColumnTypeParserFactory.instance());

        fileConverter.convert();
    }

    @Test
    public void testHeaderAndOneRowIsWritten() {

        String expectedHeader = "column-1,column-2,column-3";
        String expectedParsedLine = "thisistwentycharword,01/12/2012,34.12";

        String inputFile = System.getProperty("user.dir") + "/src/test/resources/input.txt";
        String outFile = System.getProperty("user.dir") + "/src/test/resources/output.txt";
        AppConfig appConfig = new AppConfig(inputFile, outFile, FileFormat.CSV, Arrays.asList(
                new ColumnData(new String[]{"column-1", "20", "string"}),
                new ColumnData(new String[]{"column-2", "10", "date"}),
                new ColumnData(new String[]{"column-3", "5", "numeric"})));

        FileConverter fileConverter = new CSVFileConverter(appConfig, DefaultColumnTypeParserFactory.instance());

        fileConverter.convert();

        try {
            List<String> lines = Files.readAllLines(Paths.get(outFile));
            System.out.println(lines);
            assertEquals(2, lines.size());
            assertEquals(expectedHeader, lines.get(0));
            assertEquals(expectedParsedLine, lines.get(1));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }


    }

}