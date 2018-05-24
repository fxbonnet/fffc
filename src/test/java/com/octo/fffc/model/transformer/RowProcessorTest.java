package com.octo.fffc.model.transformer;

import com.octo.fffc.exceptions.TransformationException;
import com.octo.fffc.model.metadata.Metadata;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.octo.fffc.model.metadata.DataType.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class RowProcessorTest {

    @Test
    public void testValidInputData() {

        String input = "1970-01-01John           Smith           81.5";

        List<Metadata> metadatas = new ArrayList<>();
        Metadata firstColum = Mockito.mock(Metadata.class);
        when(firstColum.getStartIndex()).thenReturn(0);
        when(firstColum.getEndIndex()).thenReturn(10);
        when(firstColum.getDataType()).thenReturn(DATE);
        metadatas.add(firstColum);

        Metadata secondColumn = Mockito.mock(Metadata.class);
        when(secondColumn.getStartIndex()).thenReturn(10);
        when(secondColumn.getEndIndex()).thenReturn(25);
        when(secondColumn.getDataType()).thenReturn(STRING);
        metadatas.add(secondColumn);

        Metadata thirdColumn = Mockito.mock(Metadata.class);
        when(thirdColumn.getStartIndex()).thenReturn(25);
        when(thirdColumn.getEndIndex()).thenReturn(40);
        when(thirdColumn.getDataType()).thenReturn(STRING);
        metadatas.add(thirdColumn);

        Metadata fourthColumn = Mockito.mock(Metadata.class);
        when(fourthColumn.getStartIndex()).thenReturn(40);
        when(fourthColumn.getEndIndex()).thenReturn(45);
        when(fourthColumn.getDataType()).thenReturn(NUMERIC);
        metadatas.add(fourthColumn);

        RowProcessor rowProcessor = new RowProcessor(metadatas);
        String[] values = rowProcessor.apply(input);

        assertEquals("01/01/1970", values[0]);
        assertEquals("John", values[1]);
        assertEquals("Smith", values[2]);
        assertEquals("81.5", values[3]);
    }

    @Test(expected = TransformationException.class)
    public void testInValidInputDateFormat() {
        String input = "01-01-1970John           Smith           81.5";

        List<Metadata> metadatas = new ArrayList<>();
        Metadata firstColum = Mockito.mock(Metadata.class);
        when(firstColum.getStartIndex()).thenReturn(0);
        when(firstColum.getEndIndex()).thenReturn(10);
        when(firstColum.getDataType()).thenReturn(DATE);
        metadatas.add(firstColum);

        Metadata secondColumn = Mockito.mock(Metadata.class);
        when(secondColumn.getStartIndex()).thenReturn(10);
        when(secondColumn.getEndIndex()).thenReturn(25);
        when(secondColumn.getDataType()).thenReturn(STRING);
        metadatas.add(secondColumn);

        Metadata thirdColumn = Mockito.mock(Metadata.class);
        when(thirdColumn.getStartIndex()).thenReturn(25);
        when(thirdColumn.getEndIndex()).thenReturn(40);
        when(thirdColumn.getDataType()).thenReturn(STRING);
        metadatas.add(thirdColumn);

        Metadata fourthColumn = Mockito.mock(Metadata.class);
        when(fourthColumn.getStartIndex()).thenReturn(40);
        when(fourthColumn.getEndIndex()).thenReturn(45);
        when(fourthColumn.getDataType()).thenReturn(NUMERIC);
        metadatas.add(fourthColumn);

        RowProcessor rowProcessor = new RowProcessor(metadatas);
        String[] values = rowProcessor.apply(input);

        assertEquals("01/01/1970", values[0]);
        assertEquals("John", values[1]);
        assertEquals("Smith", values[2]);
        assertEquals("81.5", values[3]);
    }

    @Test (expected = TransformationException.class)
    public void testInCompleteInputData() {
        String input = "1970-01-01John           Smith          ";

        List<Metadata> metadatas = new ArrayList<>();
        Metadata firstColum = Mockito.mock(Metadata.class);
        when(firstColum.getStartIndex()).thenReturn(0);
        when(firstColum.getEndIndex()).thenReturn(10);
        when(firstColum.getDataType()).thenReturn(DATE);
        metadatas.add(firstColum);

        Metadata secondColumn = Mockito.mock(Metadata.class);
        when(secondColumn.getStartIndex()).thenReturn(10);
        when(secondColumn.getEndIndex()).thenReturn(25);
        when(secondColumn.getDataType()).thenReturn(STRING);
        metadatas.add(secondColumn);

        Metadata thirdColumn = Mockito.mock(Metadata.class);
        when(thirdColumn.getStartIndex()).thenReturn(25);
        when(thirdColumn.getEndIndex()).thenReturn(40);
        when(thirdColumn.getDataType()).thenReturn(STRING);
        metadatas.add(thirdColumn);

        Metadata fourthColumn = Mockito.mock(Metadata.class);
        when(fourthColumn.getStartIndex()).thenReturn(40);
        when(fourthColumn.getEndIndex()).thenReturn(45);
        when(fourthColumn.getDataType()).thenReturn(NUMERIC);
        metadatas.add(fourthColumn);

        RowProcessor rowProcessor = new RowProcessor(metadatas);
        String[] values = rowProcessor.apply(input);

        assertEquals("01/01/1970", values[0]);
        assertEquals("John", values[1]);
        assertEquals("Smith", values[2]);
        assertEquals("81.5", values[3]);
    }

    @Test (expected = TransformationException.class)
    public void testNullInputData() {
        List<Metadata> metadatas = new ArrayList<>();
        Metadata firstColum = Mockito.mock(Metadata.class);
        when(firstColum.getStartIndex()).thenReturn(0);
        when(firstColum.getEndIndex()).thenReturn(10);
        when(firstColum.getDataType()).thenReturn(DATE);
        metadatas.add(firstColum);

        Metadata secondColumn = Mockito.mock(Metadata.class);
        when(secondColumn.getStartIndex()).thenReturn(10);
        when(secondColumn.getEndIndex()).thenReturn(25);
        when(secondColumn.getDataType()).thenReturn(STRING);
        metadatas.add(secondColumn);

        Metadata thirdColumn = Mockito.mock(Metadata.class);
        when(thirdColumn.getStartIndex()).thenReturn(25);
        when(thirdColumn.getEndIndex()).thenReturn(40);
        when(thirdColumn.getDataType()).thenReturn(STRING);
        metadatas.add(thirdColumn);

        Metadata fourthColumn = Mockito.mock(Metadata.class);
        when(fourthColumn.getStartIndex()).thenReturn(40);
        when(fourthColumn.getEndIndex()).thenReturn(45);
        when(fourthColumn.getDataType()).thenReturn(NUMERIC);
        metadatas.add(fourthColumn);

        RowProcessor rowProcessor = new RowProcessor(metadatas);
        rowProcessor.apply(null);
    }

    @Test (expected = TransformationException.class)
    public void testEmptyInputData() {
        String input = "";

        List<Metadata> metadatas = new ArrayList<>();
        Metadata firstColum = Mockito.mock(Metadata.class);
        when(firstColum.getStartIndex()).thenReturn(0);
        when(firstColum.getEndIndex()).thenReturn(10);
        when(firstColum.getDataType()).thenReturn(DATE);
        metadatas.add(firstColum);

        Metadata secondColumn = Mockito.mock(Metadata.class);
        when(secondColumn.getStartIndex()).thenReturn(10);
        when(secondColumn.getEndIndex()).thenReturn(25);
        when(secondColumn.getDataType()).thenReturn(STRING);
        metadatas.add(secondColumn);

        Metadata thirdColumn = Mockito.mock(Metadata.class);
        when(thirdColumn.getStartIndex()).thenReturn(25);
        when(thirdColumn.getEndIndex()).thenReturn(40);
        when(thirdColumn.getDataType()).thenReturn(STRING);
        metadatas.add(thirdColumn);

        Metadata fourthColumn = Mockito.mock(Metadata.class);
        when(fourthColumn.getStartIndex()).thenReturn(40);
        when(fourthColumn.getEndIndex()).thenReturn(45);
        when(fourthColumn.getDataType()).thenReturn(NUMERIC);
        metadatas.add(fourthColumn);

        RowProcessor rowProcessor = new RowProcessor(metadatas);
        rowProcessor.apply(input);
    }
}
