package com.truform.fffc;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FFFCTest {
    private static String dataFormat = "target/test-classes/%s-data";
    private static String metadataFormat = "target/test-classes/%s-metadata";
    private static String outputFormat = "target/test-classes/%s-output";

    @Test
    public void testMainEmpty() {
        String prefix = "empty";
        String dataFile = String.format(dataFormat, prefix);
        String metadataFile = String.format(metadataFormat, prefix);
        String outputFile = String.format(outputFormat, prefix);

        String[] filePaths = {dataFile, metadataFile, outputFile};
        FFFC.main(filePaths);

        try {
            assert Files.readAllLines(Paths.get(outputFile))
                    .stream()
                    .collect(Collectors.joining("\n"))
                    .equals("");
        } catch (IOException e) {
            Assert.fail("File IO error during test");
        }
    }

    @Test
    public void testMainFull() {
        String prefix = "working";
        String dataFile = String.format(dataFormat, prefix);
        String metadataFile = String.format(metadataFormat, prefix);
        String outputFile = String.format(outputFormat, prefix);

        String[] filePaths = {dataFile, metadataFile, outputFile};
        FFFC.main(filePaths);

        try {
            assert Files.readAllLines(Paths.get(outputFile))
                    .stream()
                    .collect(Collectors.joining("\n"))
                    .equals("Birth date,First name,Last name,Weight\n" +
                            "01/01/1970,John,Smith,81.5\n" +
                            "31/01/1975,Jane,Doe,61.1\n" +
                            "28/11/1988,Bob,Big,102.4");
        } catch (IOException e) {
            Assert.fail("File IO error during test");
        }
    }
}