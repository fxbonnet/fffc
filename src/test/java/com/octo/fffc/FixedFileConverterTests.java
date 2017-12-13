package com.octo.fffc;

import com.octo.fffc.model.ColumnStructure;

import com.octo.fffc.model.ColumnType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class FixedFileConverterTests {

    @Test
    public void testGenerateCsvFile() {
        FixedFileConverter ffc = new FixedFileConverter(
                this.getClass()
                        .getClassLoader()
                        .getResource("example1/metadata.txt")
                        .getPath(), this.getClass()
                .getClassLoader()
                .getResource("example1/data.txt")
                .getPath()
        );

        ffc.generateCsvFile();

        Path outputPath = Paths.get(this.getClass().getClassLoader().getResource("example1").getPath() + "/data.csv");

        Assert.assertTrue(Files.exists(outputPath));
    }

    @Test
    public void testGetColumnStructure() {

        FixedFileConverter ffc = new FixedFileConverter(this.getClass()
                .getClassLoader()
                .getResource("example1/metadata.txt")
                .getPath(), "");

        List<ColumnStructure> columnStructureList = ffc.getColumnsStructure();

        assertEquals(4, columnStructureList.size());

        assertColumnStructure("Birth date", 10, ColumnType.DATE, columnStructureList.get(0));
        assertColumnStructure("First name", 15, ColumnType.STRING, columnStructureList.get(1));
        assertColumnStructure("Last name", 15, ColumnType.STRING, columnStructureList.get(2));
        assertColumnStructure("Weight", 5, ColumnType.NUMERIC, columnStructureList.get(3));
    }

    @Test
    public void testGetHeader() {

        FixedFileConverter ffc = new FixedFileConverter(this.getClass()
                .getClassLoader()
                .getResource("example1/metadata.txt")
                .getPath(), "");

        List<ColumnStructure> columnStructureList = new ArrayList<>();

        columnStructureList.add(new ColumnStructure("Birth date", 10, ColumnType.DATE));
        columnStructureList.add(new ColumnStructure("First name", 15, ColumnType.STRING));
        columnStructureList.add(new ColumnStructure("Last name", 15, ColumnType.STRING));
        columnStructureList.add(new ColumnStructure("Weight", 5, ColumnType.NUMERIC));

        assertEquals("Birth date,First name,Last name,Weight", ffc.getHeader(columnStructureList));
    }

    @Test
    public void testParseLine() {
        FixedFileConverter ffc = new FixedFileConverter("", "");

        List<ColumnStructure> columnStructureList = new ArrayList<>();

        columnStructureList.add(new ColumnStructure("Birth date", 10, ColumnType.DATE));
        columnStructureList.add(new ColumnStructure("First name", 15, ColumnType.STRING));
        columnStructureList.add(new ColumnStructure("Last name", 15, ColumnType.STRING));
        columnStructureList.add(new ColumnStructure("Weight", 5, ColumnType.NUMERIC));

        String parsedLine = ffc.parseLine(0, "1970-01-01John           Smith           81.5", columnStructureList);

        Assert.assertEquals("01/01/1970,John,Smith,81.5", parsedLine);
    }

    @Test
    public void testDoConvertFile() throws IOException {
        FixedFileConverter ffc = new FixedFileConverter(
                this.getClass()
                        .getClassLoader()
                        .getResource("example1/metadata.txt")
                        .getPath(), this.getClass()
                .getClassLoader()
                .getResource("example1/data.txt")
                .getPath()
        );

        List<ColumnStructure> columnStructureList = new ArrayList<>();

        columnStructureList.add(new ColumnStructure("Birth date", 10, ColumnType.DATE));
        columnStructureList.add(new ColumnStructure("First name", 15, ColumnType.STRING));
        columnStructureList.add(new ColumnStructure("Last name", 15, ColumnType.STRING));
        columnStructureList.add(new ColumnStructure("Weight", 5, ColumnType.NUMERIC));

        ffc.doConvertInputFile(columnStructureList);

        Path outputPath = Paths.get(this.getClass().getClassLoader().getResource("example1").getPath() + "/data.csv");

        List<String> fileOutputList = new ArrayList<>();

        Stream<String> stream = Files.lines(outputPath);
        stream.forEach((String line) -> fileOutputList.add(line));

        Assert.assertEquals(4, fileOutputList.size());
        Assert.assertEquals("Birth date,First name,Last name,Weight", fileOutputList.get(0));
        Assert.assertEquals("01/01/1970,John,Smith,81.5", fileOutputList.get(1));
        Assert.assertEquals("31/01/1975,Jane,Doe,61.1", fileOutputList.get(2));
        Assert.assertEquals("28/11/1988,Bob,Big,102.4", fileOutputList.get(3));
    }

    @Test
    public void testDoConvertFileWithSpecialCharacters() throws IOException {
        FixedFileConverter ffc = new FixedFileConverter(
                this.getClass()
                        .getClassLoader()
                        .getResource("example2/metadata.txt")
                        .getPath(), this.getClass()
                .getClassLoader()
                .getResource("example2/data.txt")
                .getPath()
        );

        List<ColumnStructure> columnStructureList = new ArrayList<>();

        columnStructureList.add(new ColumnStructure("Birth date", 10, ColumnType.DATE));
        columnStructureList.add(new ColumnStructure("First name", 20, ColumnType.STRING));
        columnStructureList.add(new ColumnStructure("Last name", 20, ColumnType.STRING));
        columnStructureList.add(new ColumnStructure("Balance", 6, ColumnType.NUMERIC));

        ffc.doConvertInputFile(columnStructureList);

        Path outputPath = Paths.get(this.getClass().getClassLoader().getResource("example2").getPath() + "/data.csv");

        List<String> fileOutputList = new ArrayList<>();

        Stream<String> stream = Files.lines(outputPath);
        stream.forEach((String line) -> fileOutputList.add(line));

        Assert.assertEquals(4, fileOutputList.size());
        Assert.assertEquals("Birth date,First name,Last name,Balance", fileOutputList.get(0));
        Assert.assertEquals("01/01/1970,José,Smith,71.5", fileOutputList.get(1));
        Assert.assertEquals("31/01/1975,Jörg,Doe,-61.1", fileOutputList.get(2));
        Assert.assertEquals("28/11/1988,Bob,Big,-102.4", fileOutputList.get(3));
    }

    private void assertColumnStructure(String expectedColumnName, Integer expectedColumnLength, ColumnType expectedColumnType, ColumnStructure columnStructure) {
        assertEquals(expectedColumnName, columnStructure.getColumnName());
        assertEquals(expectedColumnLength, columnStructure.getLength());
        assertEquals(expectedColumnType, columnStructure.getType());
    }

}
