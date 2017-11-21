package com.arun.octo;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class ParserTest {
    private Parser parser;
    private final File metadata = new File(Thread.currentThread().getContextClassLoader().getResource("md-s1").toURI());
    private final File dataFile = new File(Thread.currentThread().getContextClassLoader().getResource("df-s1").toURI());

    public ParserTest() throws URISyntaxException {
    }

    @Test
    public void testMetadata() throws Exception {
        // Arrange
        parser = new Parser(metadata, dataFile);
        assertThat(parser, notNullValue());

        // Act
        MetaData md = parser.parseMetaData();
        Column[] columns = md.getColumns();

        // Assertions
        assertThat(md, notNullValue());
        assertThat(columns, notNullValue());
        assertThat(columns.length, is(4));

    }

    @Test
    public void testDataFileParse() throws Exception {
        // Arrange
        parser = new Parser(metadata, dataFile);
        assertThat(parser, notNullValue());

        // Act
        MetaData md = parser.parseMetaData();
        List<String> records = parser.parseData(md);

        // Assertions
        assertThat(records, notNullValue());
        assertThat(records.size(), is(3));
    }

    @Test
    public void testWriteCsvFile() throws Exception {
        // Arrange
        parser = new Parser(metadata, dataFile);
        assertThat(parser, notNullValue());

        // Act
        MetaData md = parser.parseMetaData();
        List<String> records = parser.parseData(md);
        parser.write(md.getColumns(), records, "outputfile.csv");

        // Assertions
        assertThat(Files.exists(Paths.get("outputfile.csv")), is(true));
    }
}