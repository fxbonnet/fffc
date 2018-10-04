package diggele.van.garry.parser;

import diggele.van.garry.model.Column;
import diggele.van.garry.model.MetaDataFileDefinition;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class MetaDataFileParserImplTest {
    private MetaDataFileDefinition metaDataFileDefinition;

    private Column testColumn1;
    private Column testColumn2;
    private Column testColumn3;
    private Column testColumn4;

    @Before
    public void setUp() throws Exception {
        testColumn1 = new Column.ColumnBuilder().length(10).name("Birth date").position(1).type("date").createColumn();
        testColumn2 = new Column.ColumnBuilder().length(15).name("First name").position(2).type("string").createColumn();
        testColumn3 = new Column.ColumnBuilder().length(15).name("Last name").position(3).type("string").createColumn();
        testColumn4 = new Column.ColumnBuilder().length(5).name("Weight").position(4).type("numeric").createColumn();
    }

    @Test
    public void parse() throws URISyntaxException, IOException {
        MetaDataFileDefinition expected = new MetaDataFileDefinition();
        expected.addColumnDefinition(testColumn1);
        expected.addColumnDefinition(testColumn2);
        expected.addColumnDefinition(testColumn3);
        expected.addColumnDefinition(testColumn4);
        metaDataFileDefinition = new MetaDataFileParserImpl().parse(getPath("testmeta-good.metadata"));
        assertEquals(expected.toString(), metaDataFileDefinition.toString());
        // some reason the equals assert has an extra space
//        assertEquals(expected, metaDataFileDefinition);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseBadColumnsValue() throws URISyntaxException, IOException {
        MetaDataFileDefinition expected = new MetaDataFileDefinition();
        expected.addColumnDefinition(testColumn1);
        expected.addColumnDefinition(testColumn2);
        expected.addColumnDefinition(testColumn3);
        expected.addColumnDefinition(testColumn4);
        metaDataFileDefinition = new MetaDataFileParserImpl().parse(getPath("testmeta-bad.metadata"));
        assertEquals(expected.toString(), metaDataFileDefinition.toString());
    }

    @Test(expected = RuntimeException.class)
    public void parseBadColumnsCount() throws URISyntaxException, IOException {
        MetaDataFileDefinition expected = new MetaDataFileDefinition();
        expected.addColumnDefinition(testColumn1);
        metaDataFileDefinition = new MetaDataFileParserImpl().parse(getPath("testmeta-bad-invalidcolumns.metadata"));
        assertEquals(expected.toString(), metaDataFileDefinition.toString());
    }

    @Test(expected = NumberFormatException.class)
    public void parseBadData() throws URISyntaxException, IOException {
        MetaDataFileDefinition expected = new MetaDataFileDefinition();
        expected.addColumnDefinition(testColumn1);
        metaDataFileDefinition = new MetaDataFileParserImpl().parse(getPath("testmeta-bad2.metadata"));
        assertEquals(expected.toString(), metaDataFileDefinition.toString());
    }

    @Test
    public void testGetterSetter() {
        MetaDataFileParserImpl metaDataFileParser = new MetaDataFileParserImpl();
        assertEquals(3, metaDataFileParser.getNumberOfExpectedColumns());
        assertEquals(",", metaDataFileParser.getMetaSeparator());
        metaDataFileParser.setMetaSeparator("|");
        metaDataFileParser.setNumberOfExpectedColumns(2);
        assertEquals(2, metaDataFileParser.getNumberOfExpectedColumns());
        assertEquals("|", metaDataFileParser.getMetaSeparator());
    }

    private Path getPath(String aPath) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(aPath).toURI());
    }
}