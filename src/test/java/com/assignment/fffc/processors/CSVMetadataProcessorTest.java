package com.assignment.fffc.processors;

import com.assignment.fffc.model.Column;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CSVMetadataProcessorTest {

    @Autowired
    CSVMetadataProcessor csvMetadataProcessor;

    @Test
    public void shouldExtractColumnsFromMetaDataFile() throws Exception {
        List<Column> expectedColumns = new ArrayList<Column>();
        expectedColumns.add(new Column("Birth date", 10, "date"));
        expectedColumns.add(new Column("First name", 15, "string"));
        expectedColumns.add(new Column("Last name", 15, "string"));
        expectedColumns.add(new Column("Weight", 5, "numeric"));

        List<Column> actualColumns = csvMetadataProcessor.extractMetaData("src/test/resources/files/metadata.txt");

        assertEquals(expectedColumns,actualColumns);
    }
}