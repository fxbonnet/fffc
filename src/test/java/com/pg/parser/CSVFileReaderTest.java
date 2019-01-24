package com.pg.parser;

import com.pg.parser.impl.CSVFileReaderImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CSVFileReaderTest {
    CSVFileReader underTest;

    @Test
    public void testGetColumns() {

        underTest = new CSVFileReaderImpl("metadata.csv", ",");
        List<Column> columns = underTest.getColumns();
        Assert.assertTrue(columns != null && !columns.isEmpty() && columns.size() == 5);
        Assert.assertEquals("Birth date", columns.get(0).getName());
        Assert.assertEquals("date", columns.get(0).getType());
        Assert.assertEquals(new Integer(10), columns.get(0).getLength());
    }

    @Test
    public void testMissingMetadata() {

        underTest = new CSVFileReaderImpl("metadata1.csv" , ",");
        List<Column> columns = underTest.getColumns();
        Assert.assertTrue(columns != null && columns.isEmpty() );
    }
}
