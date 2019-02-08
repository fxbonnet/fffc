package com.assignment.fffc.processors;

import com.assignment.fffc.formats.ColumnFormatProvider;
import com.assignment.fffc.model.Column;
import com.assignment.fffc.model.ColumnType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CSVDataProcessorTest {

    @Mock
    private ColumnFormatProvider columnFormatProvider;

    @InjectMocks
    private CSVDataProcessor csvDataProcessor;

    private List<Column> columns;

    @Before
    public void setUp() {
        columns = new ArrayList<Column>();
        columns.add(new Column("Birth date", 10, "date"));
        columns.add(new Column("First name", 15, "string"));
        columns.add(new Column("Last name", 15, "string"));
        columns.add(new Column("Weight", 5, "numeric"));

    }

    @Test
    public void shouldFormatStringToCsv() {

        when(columnFormatProvider.format(ColumnType.DATE.toString())).thenReturn(x -> DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.parse(x)));
        when(columnFormatProvider.format(ColumnType.NUMERIC.toString())).thenReturn(x -> Double.parseDouble(x.trim()));
        when(columnFormatProvider.format(ColumnType.STRING.toString())).thenReturn(x -> Pattern.compile("\\p{Punct}").matcher(x.trim()).find() ?
                ("\"" + x.trim() + "\"") : x.trim());
        String formattedString = csvDataProcessor.process("1970-02-06John,y         Smith           81.5", columns);
        assertEquals("06/02/1970,\"John,y\",Smith,81.5\n",formattedString);
    }
}