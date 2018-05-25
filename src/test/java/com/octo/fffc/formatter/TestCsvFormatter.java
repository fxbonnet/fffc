package com.octo.fffc.formatter;

import com.octo.fffc.Configurator;
import com.octo.fffc.exception.InvalidInputException;
import com.octo.fffc.metadata.ColumnDefinition;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.octo.fffc.metadata.ColumnDefinition.ColumnDefinitionBuilder;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCsvFormatter {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private List<ColumnDefinition> columns;
    private CsvFormatter formatter;

    @Before
    public void setup() throws InvalidInputException {
        // Column definitions
        columns = new ArrayList<ColumnDefinition>() {
            {
                add(getColumnDefinition("Birth date", "10", "date"));
                add(getColumnDefinition("First name", "15", "string"));
                add(getColumnDefinition("Last name", "15", "string"));
                add(getColumnDefinition("Weight", "5", "numeric"));
            }
        };

        // Desired Properties
        Configurator config = new Configurator();
        config.setFieldDelimiter(",");
        config.setInputDateFormat("yyyy-mm-dd");
        config.setOutputDateFormat("dd/mm/yyyy");

        formatter = new CsvFormatter(config);
    }

    private ColumnDefinition getColumnDefinition(String... fields) throws InvalidInputException {
        return new ColumnDefinitionBuilder().setName(fields[0])
                .setLength(fields[1])
                .setType(fields[2])
                .build();
    }

    @Test
    public void testFormatterWithValidInputAndDefinitions() {
        String input = "1970-01-01John           Smith           81.5";
        String expectedOutput = "01/01/1970,John,Smith,81.5";
        Optional<String> output = formatter.format(input, columns);
        assertEquals(expectedOutput, output.get());
    }

    @Test
    public void testFormatterWithInvalidDate() {
        String input = "1970-01001John           Smith           81.5";
        Optional<String> output = formatter.format(input, columns);
        assertFalse(output.isPresent());
    }

    @Test
    public void testFormatterWithMoreColumnsThanDefinition() {
        String input = "1970-01-01John           Smith           81.51970-01001John           Smith           81.5";
        Optional<String> output = formatter.format(input, columns);
        String expectedOutput = "01/01/1970,John,Smith,81.5";
        assertEquals(expectedOutput, output.get());
    }

    @Test
    public void testFormatterWithLessColumnsThanDefinition() {
        String input = "1970-01-01John           Smith           ";
        Optional<String> output = formatter.format(input, columns);
        assertFalse(output.isPresent());
    }

    @Test
    public void testFormatterWithInvalidDataType() {
        String input = "1970-01-01John           Smith           Risk";
        Optional<String> output = formatter.format(input, columns);
        assertFalse(output.isPresent());
    }

    @Test
    public void testFormatterWithDelimiterInTheInput() {
        String input = "1970-01-01John,,,        Smith           81.5";
        String expectedOutput = "01/01/1970,\"John,,,\",Smith,81.5";
        Optional<String> output = formatter.format(input, columns);
        assertEquals(expectedOutput, output.get());
    }
}
