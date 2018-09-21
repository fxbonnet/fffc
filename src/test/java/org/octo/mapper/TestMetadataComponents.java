package org.octo.mapper;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class TestMetadataComponents {

    MetadataComponents metadataComponents = new MetadataComponents();

    @Test
    public void positiveParsing() throws Exception{
        String metadataFile = "src/main/resources/samples/metadata.txt";
        metadataComponents = MetadataComponents.getMetadata(metadataFile);
        assertEquals(4, metadataComponents.getFieldsType().length);
        assertEquals(4, metadataComponents.getFieldNames().length);
        assertEquals(4, metadataComponents.getFieldsSize().length);
        assertEquals(1, metadataComponents.getDateTypeIndex().length);
    }

    @Test(expected = Exception.class)
    public void negativeParsing() throws Exception{
        String metadataFile = "src/main/resources/samples/data.txt";
        metadataComponents = MetadataComponents.getMetadata(metadataFile);
    }

    @Test(expected = FileNotFoundException.class)
    public void fileNoteFoundTest() throws Exception{
        String metadataFile = "src/main/resources/samples/abc.txt";
        metadataComponents = MetadataComponents.getMetadata(metadataFile);
    }
}
