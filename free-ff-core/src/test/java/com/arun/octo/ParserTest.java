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
    private final String metaDataFileName = "md-s3";
    private final String dataFileName = "df-s3";
    private final File metadata = new File(Thread.currentThread().getContextClassLoader().getResource(metaDataFileName).toURI());
    private final File dataFile = new File(Thread.currentThread().getContextClassLoader().getResource(dataFileName).toURI());

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
        String csvfilename = dataFileName + ".csv";
        parser = new Parser(metadata, dataFile);
        assertThat(parser, notNullValue());

        // Act
        MetaData md = parser.parseMetaData();
        List<String> records = parser.parseData(md);
        parser.write(md.getColumns(), records, csvfilename);

        // Assertions
        assertThat(Files.exists(Paths.get(csvfilename)), is(true));
    }
}