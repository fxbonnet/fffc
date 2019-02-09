package com.assignment.fffc.processors;

import com.assignment.fffc.model.Column;
import com.pivovarit.function.exception.WrappedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CSVMetadataProcessorTest {

    @Autowired
    CSVMetadataProcessor csvMetadataProcessor;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private List<Column> prepareData() {
        List<Column> expectedColumns = new ArrayList<Column>();
        expectedColumns.add(new Column("Birth date", 10, "date"));
        expectedColumns.add(new Column("First name", 15, "string"));
        expectedColumns.add(new Column("Last name", 15, "string"));
        expectedColumns.add(new Column("Weight", 5, "numeric"));
        return expectedColumns;
    }

    @Test
    public void shouldExtractColumnsFromMetaDataFile() throws Exception {

        List<Column> actualColumns = csvMetadataProcessor.extractMetaData("src/test/resources/files/metadata.txt");
        assertEquals(prepareData(), actualColumns);
    }

    @Test
    public void shouldFailWhenColumnSizeIsNegativeOrZero() throws Exception {
        exception.expect(WrappedException.class);
        exception.expectMessage("Column Size Cannot be Zero Or Negative");

        csvMetadataProcessor.extractMetaData("src/test/resources/files/metadata-NegativeSize.txt");
    }

    @Test
    public void shouldFailWhenInvalidFilePath() throws Exception {
        exception.expect(NoSuchFileException.class);
        exception.expectMessage("src/test/resources/files/metadataa.txt");
        csvMetadataProcessor.extractMetaData("src/test/resources/files/metadataa.txt");
    }

    @Test
    public void shouldFailWhenNullOrEmptyFilePath() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("metadataFilePath is null or empty");
        csvMetadataProcessor.extractMetaData("");
    }

    @Test
    public void shouldFailWhenAllColumnDefinitionsAreNotPresent() throws Exception {
        exception.expect(WrappedException.class);
        exception.expectMessage("Invalid Column Definition");
        csvMetadataProcessor.extractMetaData("src/test/resources/files/metadata-invalidColumnDefinition.txt");
    }

    @Test
    public void shouldFailWhenColumnSizeIsNotNumeric() throws Exception {
        exception.expect(WrappedException.class);
        exception.expectMessage("Specified Column Size is not Numeric");
        csvMetadataProcessor.extractMetaData("src/test/resources/files/metadata-InvalidColumnSize.txt");
    }


}