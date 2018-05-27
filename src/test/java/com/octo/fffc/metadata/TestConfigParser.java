package com.octo.fffc.metadata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class TestConfigParser {

    private ConfigParser parser = new ConfigParserImpl(new ColumnDefinitionExtractor());
    private static String RESOURCES;

    @Before
    public void setup() {
        RESOURCES = new File("src/test/resources/metadata/").getAbsolutePath();
    }

    @Test
    public void testMetadataFileParserWithNonExistentFile() {
        List<ColumnDefinition> definitions = parser.parseFile("adsf");
        Assert.assertFalse(definitions.size() > 0);
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
        List<ColumnDefinition> definitions = parser.parseFile(RESOURCES + File.separator + "few_invalid_file");
        Assert.assertTrue(definitions.size() < 1);
    }
}