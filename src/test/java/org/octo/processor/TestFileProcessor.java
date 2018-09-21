package org.octo.processor;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestFileProcessor {

    private String metadataFile = "src/main/resources/samples/metadata.txt";
    private String inputFile = "src/main/resources/samples/data.txt";
    private String outputFile = "src/main/resources/samples/output.txt";
    private String expectedFile = "src/main/resources/samples/expected.txt";


    public static String readFile(String path) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(path));
        return new String(fileContent);
    }

    @Test
    public void positiveFileParsing_recordCountMatch() throws Exception {
        FileProcessor fileProcessor = new FileProcessor(metadataFile);
        fileProcessor.parseFixedWidthFile(inputFile, outputFile);
        Stream<String> inputFileLines = Files.lines(Paths.get(inputFile));
        Stream<String> outputFileLines = Files.lines(Paths.get(outputFile));
        assertEquals(inputFileLines.count(), outputFileLines.count());
        assertTrue(readFile(expectedFile).contains(readFile(outputFile)));
    }

    @Test(expected = Exception.class)
    public void negativeFileParsing_inputFileNotPresent() throws Exception {
        String inputFile = "src/main/resources/samples/abc.txt";
        FileProcessor fileProcessor = new FileProcessor(metadataFile);
        fileProcessor.parseFixedWidthFile(inputFile, outputFile);
    }
}
