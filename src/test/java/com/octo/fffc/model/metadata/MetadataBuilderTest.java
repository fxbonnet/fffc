package com.octo.fffc.model.metadata;

import com.octo.fffc.exceptions.MetadataBuilderException;
import org.junit.Test;

import static com.octo.fffc.model.metadata.DataType.DATE;
import static com.octo.fffc.model.metadata.DataType.NUMERIC;
import static com.octo.fffc.model.metadata.DataType.STRING;
import static com.octo.fffc.model.metadata.MetadataBuilder.build;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetadataBuilderTest {

    @Test
    public void testValidInputFirstRecord() {
        String metadataline = "Birth date,10,date";
        Metadata metadata = build(metadataline, null);

        assertEquals("Birth date", metadata.getColumnName());
        assertEquals(0, metadata.getStartIndex());
        assertEquals(10, metadata.getEndIndex());
        assertEquals(DATE, metadata.getDataType());
    }

    @Test
    public void testValidInputSecondRecord() {
        String metadataline = "First name,15,string";

        Metadata prevmetada = mock(Metadata.class);
        when(prevmetada.getEndIndex()).thenReturn(10);

        Metadata metadata = build(metadataline, prevmetada);

        assertEquals("First name", metadata.getColumnName());
        assertEquals(10, metadata.getStartIndex());
        assertEquals(25, metadata.getEndIndex());
        assertEquals(STRING, metadata.getDataType());
    }

    @Test
    public void testValidInputSecondRecordNumbericType() {
        String metadataline = "Weight,5,numeric";

        Metadata prevmetada = mock(Metadata.class);
        when(prevmetada.getEndIndex()).thenReturn(10);

        Metadata metadata = build(metadataline, prevmetada);

        assertEquals("Weight", metadata.getColumnName());
        assertEquals(10, metadata.getStartIndex());
        assertEquals(15, metadata.getEndIndex());
        assertEquals(NUMERIC, metadata.getDataType());
    }

    @Test (expected = MetadataBuilderException.class)
    public void testInvalidInputMetadata() {
        String metadataline = "Weight,5,";
        build(metadataline,null);
    }

    @Test (expected = MetadataBuilderException.class)
    public void testInvalidLength() {
        String metadataline = "Weight,a,numeric";
        build(metadataline,null);
    }

    @Test (expected = MetadataBuilderException.class)
    public void testInvalidDataType() {
        String metadataline = "Weight,10,char";
        build(metadataline,null);
    }
}