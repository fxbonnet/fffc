package test.file.converter;

import org.junit.Test;

import java.text.ParseException;

public class FixedFileFormatConverterTest {

    private FixedFileFormatConverter converter = new FixedFileFormatConverter();

    @Test
    public void testExampleFiles() throws ParseException {
        String inputFileName = "Tests/input.txt";
        String metadataFileName = "Tests/metadata.txt";
        String outputFileName = "Tests/output.csv";

        converter.convertFile(inputFileName, metadataFileName, outputFileName);
    }

    @Test
    public void testMoreFields() throws ParseException {
        String inputFileName = "Tests/input01.txt";
        String metadataFileName = "Tests/metadata01.txt";
        String outputFileName = "Tests/output01.csv";

        converter.convertFile(inputFileName, metadataFileName, outputFileName);
    }

    @Test
    public void testMoreFields1000() throws ParseException {
        String inputFileName = "Tests/input02.txt";
        String metadataFileName = "Tests/metadata02.txt";
        String outputFileName = "Tests/output02.csv";

        converter.convertFile(inputFileName, metadataFileName, outputFileName);
    }
}