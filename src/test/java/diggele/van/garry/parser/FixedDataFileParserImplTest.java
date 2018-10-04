package diggele.van.garry.parser;

import diggele.van.garry.model.GenericFileRepresentation;
import diggele.van.garry.model.GenericFileRow;
import diggele.van.garry.model.MetaDataFileDefinition;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static diggele.van.garry.transformer.GenericFileToCsvTransformerTest.getGenericFileRow;
import static org.junit.Assert.assertEquals;

public class FixedDataFileParserImplTest {

    private MetaDataFileDefinition metaDataFileDefinition;
    private FixedDataFileParserImpl fixedDataFileParser;
    private GenericFileRepresentation expectedGenericFileRepresentation;

    @Before
    public void setUp() throws Exception {
        metaDataFileDefinition = new MetaDataFileParserImpl().parse(getPath("testmeta-good.metadata"));
        fixedDataFileParser = new FixedDataFileParserImpl();
        fixedDataFileParser.setMetaDataFileDefinition(metaDataFileDefinition);
        expectedGenericFileRepresentation = new GenericFileRepresentation(metaDataFileDefinition);
        expectedGenericFileRepresentation.addRow(createRow("1970-01-01", "John", "Smith", "81.5"));
        expectedGenericFileRepresentation.addRow(createRow("1975-01-31", "Jane", "Doe", "61.1"));
        expectedGenericFileRepresentation.addRow(createRow("1988-11-28", "Bo'b", "Big", "102.4"));
    }

    @Test
    public void parse() throws URISyntaxException, IOException {
        GenericFileRepresentation genericFileRepresentation = fixedDataFileParser.parse(getPath("test.data"));
        assertEquals(expectedGenericFileRepresentation.toString(), genericFileRepresentation.toString());
        // same issue as others
//        assertEquals(expectedGenericFileRepresentation, genericFileRepresentation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseBad() throws URISyntaxException, IOException {
        GenericFileRepresentation genericFileRepresentation = fixedDataFileParser.parse(getPath("test-bad.data"));
        assertEquals(expectedGenericFileRepresentation.toString(), genericFileRepresentation.toString());
        // same issue as others
//        assertEquals(expectedGenericFileRepresentation, genericFileRepresentation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseBadColumns() throws URISyntaxException, IOException {
        GenericFileRepresentation genericFileRepresentation = fixedDataFileParser.parse(getPath("test-bad-columns.data"));
        assertEquals(expectedGenericFileRepresentation.toString(), genericFileRepresentation.toString());
        // same issue as others
//        assertEquals(expectedGenericFileRepresentation, genericFileRepresentation);
    }


    private Path getPath(String aPath) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(aPath).toURI());
    }

    private GenericFileRow createRow(String aBirthDate, String aFirstName, String aLastName, String numberWeight) throws InstantiationException, IllegalAccessException {
        return getGenericFileRow(aBirthDate, aFirstName, aLastName, numberWeight);
    }
}