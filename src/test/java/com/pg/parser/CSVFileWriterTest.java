package com.pg.parser;

import com.pg.parser.impl.CSVFileReaderImpl;
import com.pg.parser.impl.CSVFileWriterImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSVFileWriterTest {
    CSVFileWriter underTest;

    @Test
    public void testWriteColumn() {
        Writer writer = new StringWriter();
        underTest = new CSVFileWriterImpl(writer, ",", "\n");
        List<Column> columns = getColumns();
        String columnNames = columns.stream()
                .map(column -> column.getName())
                .collect(Collectors.joining(","));
        try {
            underTest.writeColumnNames(columns);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(writer.toString().trim(),columnNames);

    }


    @Test
    public void testWriteRow() {
        Writer writer = new StringWriter();
        underTest = new CSVFileWriterImpl(writer, ",", "\n");
        List<Column> columns = getColumns();
        try {
            underTest.writeRow(columns, getrow());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("01/01/1970,John", writer.toString().trim());

    }

    private String getrow() {
        return "1970-01-01John           ";
    }

    public List<Column> getColumns() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("date",10,"date"));
        columns.add(new Column("firstName",15,"string"));
        return columns;

    }
}
