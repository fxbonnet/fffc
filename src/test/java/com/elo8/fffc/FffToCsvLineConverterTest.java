package com.elo8.fffc;

import org.junit.Test;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class FffToCsvLineConverterTest {

    private MetadataHandler metadataHandler;
    private FffToCsvLineConverter csvLineConverter;

    @org.junit.Before
    public void setUp() throws Exception {
        metadataHandler = new MetadataHandler();
        csvLineConverter = new FffToCsvLineConverter();
    }

    @Test
    public void shouldReturnCSVRowWhenValidInput() throws Exception {
        String validFixedFormatRow = "1970-01-01John12345678910Smith1234567ppp 81.5";
        Path path = Paths.get("src/test/resources/metadata.txt");
        List<Metadata> metadataList = metadataHandler.collectMetadata(path);
        String expected = "01/01/1970,John12345678910,Smith1234567ppp,81.5";
        String csvRow = csvLineConverter.toCsvRow(validFixedFormatRow, metadataList);
        assertEquals(expected, csvRow);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenActualDataNotAsMetadataSpecification() throws Exception {
        String dataRow = "1970-01-01John12345678910Smith1234567ppp 81.5yyyyyy";
        Path path = Paths.get("src/test/resources/metadata.txt");
        List<Metadata> metadataList = metadataHandler.collectMetadata(path);
        csvLineConverter.toCsvRow(dataRow, metadataList);
    }

    @Test
    public void shouldHandleUTF8Character() throws Exception {
        String dataRow = "1970-05-12дравей' хора   Smith           81.9";
        Path path = Paths.get("src/test/resources/metadata.txt");
        List<Metadata> metadataList = metadataHandler.collectMetadata(path);
        String expected = "12/05/1970,дравей' хора,Smith,81.9";
        String csvRow = csvLineConverter.toCsvRow(dataRow, metadataList);
        assertEquals(expected, csvRow);
    }

    @Test
    public void shouldHandleCommaAsPartofData() throws Exception {
        String dataRow = "1970-05-12дравей' х,ора  Smith           81.9";
        Path path = Paths.get("src/test/resources/metadata.txt");
        List<Metadata> metadataList = metadataHandler.collectMetadata(path);
        String expected = "12/05/1970,\"дравей' х,ора\",Smith,81.9";
        String csvRow = csvLineConverter.toCsvRow(dataRow, metadataList);
        assertEquals(expected, csvRow);
    }

    @Test
    public void shouldHandleNegativeNumeric() throws Exception {
        String validFixedFormatRow = "1970-01-01John12345678910Smith1234567ppp-81.5";
        Path path = Paths.get("src/test/resources/metadata.txt");
        List<Metadata> metadataList = metadataHandler.collectMetadata(path);
        String expected = "01/01/1970,John12345678910,Smith1234567ppp,-81.5";
        String csvRow = csvLineConverter.toCsvRow(validFixedFormatRow, metadataList);
        assertEquals(expected, csvRow);
    }

    @Test (expected = NumberFormatException.class)
    public void shouldThrowExceptionWhenNotValidNumeric() throws Exception {
        String validFixedFormatRow = "1970-01-01John12345678910Smith1234567ppp-8d.5";
        Path path = Paths.get("src/test/resources/metadata.txt");
        List<Metadata> metadataList = metadataHandler.collectMetadata(path);
        csvLineConverter.toCsvRow(validFixedFormatRow, metadataList);
    }

}