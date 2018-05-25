package com.octo.fffc.metadata;

import com.octo.fffc.CauseMatcher;
import com.octo.fffc.Configurator;
import com.octo.fffc.exception.InvalidInputException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.StringContains.containsString;

public class TestColumnDefinitionExtractor {

    private ColumnDefinitionExtractor parser;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() {
        Configurator configurator = Mockito.mock(Configurator.class);
        Mockito.when(configurator.getFieldDelimiter()).thenReturn(",");
        parser = new ColumnDefinitionExtractor(configurator);
    }

    @Test
    public void testMetadataStringFormat() throws InvalidInputException {
        ColumnDefinition column = parser.extractDefinitions("Birth date,10,date");
        boolean columnNameIsPresent = column.getName().equals("Birth date");
        boolean columnLengthIsValid = column.getLength() == 10;
        boolean columnDataTypeIsValid = column.getType() == DataType.DATE;
        assertTrue(columnNameIsPresent && columnDataTypeIsValid && columnLengthIsValid);
    }

    @Test
    public void testMetadataWithInvalidDataType() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage(containsString("should belong to date/numeric/string"));
        parser.extractDefinitions("First name,15,string1");
    }

    @Test
    public void testMetadataWithInvalidLength() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectCause(new CauseMatcher(NumberFormatException.class, "For input string: \"anbc\""));
        expectedEx.expectMessage(containsString("length anbc specified is invalid"));
        parser.extractDefinitions("First name,anbc,string1");
    }

    @Test
    public void testMetadataWithIncorrectNumberOfFields() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage(containsString("Expecting atleast 3 fields to indicate the column - name, length, dataType"));
        parser.extractDefinitions("Hello How are you all doing?");
    }

    @Ignore
    public void testMetadataWithNonUTF8Characters() throws InvalidInputException {
        assertFalse(true);
    }
}