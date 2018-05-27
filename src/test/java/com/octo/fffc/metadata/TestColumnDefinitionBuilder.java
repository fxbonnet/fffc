package com.octo.fffc.metadata;

import com.octo.fffc.CauseMatcher;
import com.octo.fffc.exception.InvalidInputException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.octo.fffc.metadata.ColumnDefinition.ColumnDefinitionBuilder;
import static org.junit.Assert.assertTrue;

public class TestColumnDefinitionBuilder {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testfColumnDefinitionWithValidInput() throws InvalidInputException {
        String[] fields = new String[]{"Birth date", "10", "date",};
        ColumnDefinition column = new ColumnDefinitionBuilder()
                .setName(fields[0])
                .setLength(fields[1])
                .setType(fields[2])
                .build();

        assertTrue(column.getName().equals(fields[0]) &&
                column.getLength() == 10 &&
                column.getType() == DataType.DATE);
    }

    @Test
    public void testColumnDefinitionWithNegativeColumnLength() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("The column length should be greater than 0");

        String[] fields = new String[]{"Birth date", "-10", "date",};
        new ColumnDefinitionBuilder()
                .setName(fields[0])
                .setLength(fields[1])
                .setType(fields[2])
                .build();
    }

    @Test
    public void testColumnDefinitionWithNonIntegerColumnLength() throws InvalidInputException {
        {
            expectedEx.expect(InvalidInputException.class);
            expectedEx.expectMessage("The length 10.00 specified is invalid. Please specify a positive integer");
            expectedEx.expectCause(new CauseMatcher(NumberFormatException.class, "For input string: \"10.00\""));

            String[] fields = new String[]{"Birth date", "10.00", "date",};
            ColumnDefinition column = new ColumnDefinitionBuilder()
                    .setName(fields[0])
                    .setLength(fields[1])
                    .setType(fields[2])
                    .build();
        }
    }
}
