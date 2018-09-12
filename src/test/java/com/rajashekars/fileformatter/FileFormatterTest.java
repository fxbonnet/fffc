package com.rajashekars.fileformatter;

import com.rajashekars.fileformatter.metadata.InvalidMetadataFormatException;
import com.rajashekars.fileformatter.metadata.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FileFormatterTest {

    private FileFormatter fileFormatter;

    @BeforeEach
    public void setUpBeforeEachTest() {
        fileFormatter = new FileFormatterImpl(new Metadata());
    }


    @Test
    void convertToCsv_valid() throws IOException, URISyntaxException {

        InputStream dataStream = getStream("valid/datafile_valid");
        InputStream metadataStream = getStream("valid/metadata_valid");

        String expectantResult = Files.lines(getPathFromClasspath("valid/result_valid.csv")).collect(Collectors.joining("\r\n"));
        assertEquals(expectantResult, fileFormatter.convertToCsv(metadataStream, dataStream));
    }

    private InputStream getStream(String dataFile) throws IOException, URISyntaxException {
        return Files.newInputStream(getPathFromClasspath(dataFile));
    }


    @Test
    public void convertToCsv_nullStream() {

        NullPointerException npe = assertThrows(NullPointerException.class, () -> fileFormatter.convertToCsv(null, null));
        assertEquals("metadataStream", npe.getMessage());

        npe = assertThrows(NullPointerException.class, () -> fileFormatter.convertToCsv(getStream("valid/metadata_valid"), null));
        assertEquals("dataStream", npe.getMessage());

        npe = assertThrows(NullPointerException.class, () -> fileFormatter.convertToCsv(null, getStream("valid/datafile_valid")));
        assertEquals("metadataStream", npe.getMessage());
    }

    @Test
    public void convertToCsv_emptyMetadataStream() {
        InvalidMetadataFormatException emptyMetadataStreamException = assertThrows(InvalidMetadataFormatException.class, () -> fileFormatter.convertToCsv(emptyStream(), getStream("valid/datafile_valid")));
        assertEquals("Empty metadata file", emptyMetadataStreamException.getMessage());
    }

    @Test
    public void convertToCsv_emptyDatafileStream() {
        InvalidDatafileException emptyMetadataStreamException = assertThrows(InvalidDatafileException.class, () -> fileFormatter.convertToCsv(getStream("valid/metadata_valid"), emptyStream()));
        assertEquals("Empty data file", emptyMetadataStreamException.getMessage());
    }

    @Test
    public void convertToCsv_emptyStreams() {
        InvalidMetadataFormatException emptyMetadataStreamException = assertThrows(InvalidMetadataFormatException.class, () -> fileFormatter.convertToCsv(emptyStream(), emptyStream()));
        assertEquals("Empty metadata file", emptyMetadataStreamException.getMessage());
    }

    private InputStream emptyStream() {
        return new SequenceInputStream(Collections.emptyEnumeration());
    }


    @Test
    public void convertToCsv_invalidMetadata_invalidNoOfColumns() throws IOException, URISyntaxException {

        InvalidMetadataFormatException exception = assertThrows(InvalidMetadataFormatException.class,
                () -> fileFormatter.convertToCsv(getStream("invalid/metadata_invalidmetadatacolumns"), getStream("invalid/datafile_invalidmetadatacolumns")));
        assertEquals("Number of columns: 4, expected 3 columns", exception.getMessage());
    }

    @Test
    public void convertToCsv_invalidMetadata_invalidDataFormat() {
        InvalidMetadataFormatException exception = assertThrows(InvalidMetadataFormatException.class,
                () -> fileFormatter.convertToCsv(getStream("invalid/metadata_invalidmetadatadataformat"), getStream("invalid/datafile_invalidmetadatadataformat")));
        assertEquals("Invalid data format: invaliddataformattype", exception.getMessage());
    }

    @Test
    public void convertToCsv_invalidDatafile_invalidNoOfColumns() {
        InvalidDatafileException exception = assertThrows(InvalidDatafileException.class,
                () -> fileFormatter.convertToCsv(getStream("invalid/metadata_invaliddatafiledateformat"), getStream("invalid/datafile_invaliddatafiledateformat")));
        assertEquals("Invalid date format of: 01-01-1975", exception.getMessage());
    }


    private Path getPathFromClasspath(String dataFile) throws URISyntaxException {
        return Paths.get(getClass()
                .getClassLoader()
                .getResource(dataFile)
                .toURI());
    }
}
