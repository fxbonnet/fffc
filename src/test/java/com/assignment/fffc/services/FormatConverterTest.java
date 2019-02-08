package com.assignment.fffc.services;

import com.assignment.fffc.formats.HeaderFormatProvider;
import com.assignment.fffc.model.Column;
import com.assignment.fffc.processors.DataProcessor;
import com.assignment.fffc.processors.MetaDataProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FormatConverterTest {

    public static final String META_DATA_FILE_PATH = "src/test/resources/files/metadata.txt";
    public static final String DATA_FILE_PATH = "src/test/resources/files/data.txt";
    public static final String OUTPUT_FILE_NAME = "src/test/resources/files/converted-output.txt";
    public static final String FORMAT_TYPE = "csv";
    public static final String HEADER = "Birth date,First name,Last name,Weight";
    public static final String PROCESSED_STRING = "31/03/1975,Janis,Doe,61.1";
    public static final String LINE_SEPARATOR = "\n";
    public static final int EXPECTED_FILE_LENGTH = 10;

    @InjectMocks
    private FormatConverter converter;

    @Mock
    private MetaDataProcessor metadataProcessor;

    @Mock
    private DataProcessor dataProcessor;

    @Mock
    private HeaderFormatProvider headerFormatProvider;

    private List<Column> columns;

    @Before
    public void setUp() throws Exception {
        columns = new ArrayList<Column>();
        columns.add(new Column("Birth date", 10, "date"));
        columns.add(new Column("First name", 15, "string"));
        columns.add(new Column("Last name", 15, "string"));
        columns.add(new Column("Weight", 5, "numeric"));
    }

    @Test
    public void shouldConvertFixedFormatToCsv() throws Exception {

        File dataFile = new File(DATA_FILE_PATH);
        when(metadataProcessor.extractMetaData(META_DATA_FILE_PATH)).thenReturn(columns);
        when(headerFormatProvider.addHeader(FORMAT_TYPE, columns)).thenReturn(HEADER + LINE_SEPARATOR);
        when(dataProcessor.process(anyString(), eq(columns))).thenReturn(PROCESSED_STRING + LINE_SEPARATOR);
        File convertedFile = converter.convert(META_DATA_FILE_PATH, DATA_FILE_PATH, OUTPUT_FILE_NAME, FORMAT_TYPE);
        assertEquals(EXPECTED_FILE_LENGTH, Files.lines(convertedFile.toPath()).count());
        assertEquals(HEADER, Files.lines(convertedFile.toPath()).findFirst().get());
        assertEquals(true, Files.lines(convertedFile.toPath()).anyMatch(x -> x.contains(PROCESSED_STRING)));
    }
}