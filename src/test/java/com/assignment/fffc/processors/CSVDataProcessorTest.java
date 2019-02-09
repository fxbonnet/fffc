package com.assignment.fffc.processors;

import com.assignment.fffc.formats.ColumnFormatProvider;
import com.assignment.fffc.model.Column;
import com.assignment.fffc.model.ColumnType;
import com.pivovarit.function.exception.WrappedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CSVDataProcessorTest {

    public static final String EXPECTED_PROCESSED_STRING = "06/02/1970,\"John,y\",Smith,81.5\n";

    @Mock
    private ColumnFormatProvider columnFormatProvider;

    @InjectMocks
    private CSVDataProcessor csvDataProcessor;

    private List<Column> columns;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        columns = new ArrayList<Column>();
        columns.add(new Column("Birth date", 10, "date"));
        columns.add(new Column("First name", 15, "string"));
        columns.add(new Column("Last name", 15, "string"));
        columns.add(new Column("Weight", 5, "numeric"));

    }

    @Test
    public void shouldFormatStringToCsv()  {

        when(columnFormatProvider.format(ColumnType.DATE.toString())).thenReturn(x -> DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.parse(x)));
        when(columnFormatProvider.format(ColumnType.NUMERIC.toString())).thenReturn(x -> Double.parseDouble(x.trim()));
        when(columnFormatProvider.format(ColumnType.STRING.toString())).thenReturn(x -> Pattern.compile("\\p{Punct}").matcher(x.trim()).find() ?
                ("\"" + x.trim() + "\"") : x.trim());
        String formattedString = csvDataProcessor.process("1970-02-06John,y         Smith           81.5", columns);
        assertEquals(EXPECTED_PROCESSED_STRING, formattedString);
    }

    @Test
    public void shouldFailWhenEmptyLine() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("empty line in Data file");
        csvDataProcessor.process("", columns);
    }

    @Test
    public void shouldFailWhenEmptyColumnList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No Column Specified in the metaDataFile");
        csvDataProcessor.process("asdasdasd", Collections.EMPTY_LIST);
    }

    @Test
    public void shouldFailWhenStringIsInvalidAndShort()  {

        exception.expect(WrappedException.class);
        exception.expectMessage("does not match the column sizes specified in the metadata file");

        when(columnFormatProvider.format(ColumnType.DATE.toString())).thenReturn(x -> DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.parse(x)));
        when(columnFormatProvider.format(ColumnType.STRING.toString())).thenReturn(x -> Pattern.compile("\\p{Punct}").matcher(x.trim()).find() ?
                ("\"" + x.trim() + "\"") : x.trim());

        csvDataProcessor.process("1970-02-06John,y ", columns);
    }

    @Test
    public void shouldFailWhenStringIsInvalidAndTooLong() {
        exception.expect(WrappedException.class);

        when(columnFormatProvider.format(ColumnType.DATE.toString())).thenReturn(x -> DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.parse(x)));
        when(columnFormatProvider.format(ColumnType.NUMERIC.toString())).thenReturn(x -> Double.parseDouble(x.trim()));
        when(columnFormatProvider.format(ColumnType.STRING.toString())).thenReturn(x -> Pattern.compile("\\p{Punct}").matcher(x.trim()).find() ?
                ("\"" + x.trim() + "\"") : x.trim());

        csvDataProcessor.process("1970-02-06John,y         Smithasdasdasdasdasdada           81.5", columns);
    }
}