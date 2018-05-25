package com.octo.fffc.metadata;

import com.octo.fffc.CauseMatcher;
import com.octo.fffc.Configurator;
import com.octo.fffc.exception.InvalidInputException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.StringContains.containsString;

public class TestConfigParser {

    private ConfigParser parser;
    private static String RESOURCES;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        Configurator configurator = Mockito.mock(Configurator.class);
        Mockito.when(configurator.getFieldDelimiter()).thenReturn(",");
        parser = new ConfigParserImpl(new ColumnDefinitionExtractor(configurator));
        RESOURCES = new File("src/test/resources/metadata/").getAbsolutePath();
    }

    @Test
    public void testMetadataFileParserWithNonExistentFile() {
        List<ColumnDefinition> output = parser.parseFile("adsf");
        assertFalse(output.size() > 0);
    }

    @Test
    public void testMetadataFileParserWithValidFile() {
        List<ColumnDefinition> definitions = parser.parseFile(RESOURCES + File.separator + "valid_file");
        assertTrue(definitions.size() == 4);
    }

    @Test
    public void testMetadataFileParserWithEmptyFile() {
        List<ColumnDefinition> definitions = parser.parseFile(RESOURCES + File.separator + "empty_file");
        assertTrue(definitions.size() == 0);
    }

    @Test
    public void testMetadataFileWithFewInvalidRecords() {
        List<ColumnDefinition> output = parser.parseFile(RESOURCES + File.separator + "few_invalid_file");
        assertFalse(output.size() > 0);
    }
}