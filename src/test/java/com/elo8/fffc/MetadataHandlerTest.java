package com.elo8.fffc;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by gftlo80 on 25/2/18.
 */
public class MetadataHandlerTest {

    private MetadataHandler metadataHandler;

    @Before
    public void setUp() throws Exception {
        metadataHandler = new MetadataHandler();

    }

    @Test
    public void shouldCreateMetadataObjectWhenValidMetadatafile() throws Exception {
        Path path = Paths.get("src/test/resources/metadata.txt");
        List<Metadata> metadataList = metadataHandler.collectMetadata(path);
        assertNotNull(metadataList);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenInvalidTypeMetadatafile() throws Exception {
        Path path = Paths.get("src/test/resources/invalidTypeInMetadata.txt");
        metadataHandler.collectMetadata(path);
    }

    @Test (expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInvalidFormatMetadatafile() throws Exception {
        Path path = Paths.get("src/test/resources/invalidFormatMetadata.txt");
        metadataHandler.collectMetadata(path);
    }

    @Test (expected = IOException.class)
    public void shouldThrowExceptionWhenNotExistantMetadatafile() throws Exception {
        Path path = Paths.get("src/test/resources/nonExistantMetadata.txt");
        metadataHandler.collectMetadata(path);
    }

}