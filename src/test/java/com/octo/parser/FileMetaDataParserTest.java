package com.octo.parser;

import com.octo.exception.FixedFileFormatCoverterException;
import com.octo.parser.metadata.ColumnData;
import com.octo.parser.metadata.ColumnType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileMetaDataParserTest {

    private FileMetaDataParser fileMetaDataParser = new FileMetaDataParser();

    @Test(expected = FixedFileFormatCoverterException.class)
    public void testMetaDataFileNotFound() {
        fileMetaDataParser.parse("unknown-metadata.txt");
    }

    @Test(expected = FixedFileFormatCoverterException.class)
    public void testInvalidMetaData() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/unknown-metadata.txt";
        fileMetaDataParser.parse(filePath);
    }

    @Test
    public void testColumnsParsedFromMetadataFile() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/metadata.txt";
        List<ColumnData> columnDataList = fileMetaDataParser.parse(filePath);
        assertNotNull(columnDataList);
        assertEquals(3, columnDataList.size());
        assertEquals(ColumnType.STRING, columnDataList.get(0).getColumnType());
        assertEquals(20, columnDataList.get(0).getLength());
        assertEquals("column-1", columnDataList.get(0).getName());

        assertEquals(ColumnType.DATE, columnDataList.get(1).getColumnType());
        assertEquals(10, columnDataList.get(1).getLength());
        assertEquals("column-2", columnDataList.get(1).getName());

        assertEquals(ColumnType.NUMERIC, columnDataList.get(2).getColumnType());
        assertEquals(5, columnDataList.get(2).getLength());
        assertEquals("column-3", columnDataList.get(2).getName());

    }

}