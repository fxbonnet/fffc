package diggele.van.garry.transformer;

import diggele.van.garry.model.Column;
import diggele.van.garry.model.GenericFileRepresentation;
import diggele.van.garry.model.GenericFileRow;
import diggele.van.garry.model.MetaDataFileDefinition;
import diggele.van.garry.parser.MetaDataFileParserImpl;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class GenericFileToCsvTransformerTest {

    private GenericFileToCsvTransformer genericFileToCsvTransformer;
    private GenericFileRepresentation genericFileRepresentation;
    private GenericFileRepresentation genericFileRepresentationBad;

    public static GenericFileRow getGenericFileRow(final String aBirthDate, final String aFirstName, final String aLastName, final String numberWeight) throws IllegalAccessException, InstantiationException {
        Column testColumn1 = new Column.ColumnBuilder().length(10).name("Birth date").position(1).type("date").value(aBirthDate).createColumn();
        Column testColumn2 = new Column.ColumnBuilder().length(15).name("First name").position(2).type("string").value(aFirstName).createColumn();
        Column testColumn3 = new Column.ColumnBuilder().length(15).name("Last name").position(3).type("string").value(aLastName).createColumn();
        Column testColumn4 = new Column.ColumnBuilder().length(5).name("Weight").position(4).type("numeric").value(numberWeight).createColumn();
        GenericFileRow genericFileRow = new GenericFileRow();
        genericFileRow.addColumn(testColumn1);
        genericFileRow.addColumn(testColumn2);
        genericFileRow.addColumn(testColumn3);
        genericFileRow.addColumn(testColumn4);
        return genericFileRow;
    }

    public static GenericFileRow getGenericFileRowDodgy(final String aBirthDate, final String aFirstName, final String aLastName, final String numberWeight) throws IllegalAccessException, InstantiationException {
        Column testColumn1 = new Column.ColumnBuilder().length(10).name("Birth date").position(1).type("date").value(aBirthDate).createColumn();
        Column testColumn2 = new Column.ColumnBuilder().length(15).name("First name").position(2).type("string").value(aFirstName).createColumn();
        Column testColumn4 = new Column.ColumnBuilder().length(5).name("Weight").position(4).type("numeric").value(numberWeight).createColumn();
        GenericFileRow genericFileRow = new GenericFileRow();
        genericFileRow.addColumn(testColumn1);
        genericFileRow.addColumn(testColumn2);
        genericFileRow.addColumn(testColumn4);
        return genericFileRow;
    }

    @Before
    public void setUp() throws Exception {
        MetaDataFileDefinition metaDataFileDefinition = new MetaDataFileParserImpl().parse(getPath("testmeta-good.metadata"));
        genericFileRepresentation = new GenericFileRepresentation(metaDataFileDefinition);
        genericFileRepresentation.addRow(createRow("1970-01-01", "John", "Smith", "81.5"));
        genericFileRepresentation.addRow(createRow("1975-01-31", "Jane", "Doe", "61.1"));
        genericFileRepresentation.addRow(createRow("1988-11-28", "Bo'b", "Big", "102.4"));

        genericFileRepresentationBad = new GenericFileRepresentation(metaDataFileDefinition);
        genericFileRepresentationBad.addRow(createRow("1970-01-01", "John", "Smith", "81.5"));
        genericFileRepresentationBad.addRow(createRow("1975-01-31", "Jane", "Doe", "61.1"));

        // safety method in case someone forgets to instantiate
        genericFileToCsvTransformer = new GenericFileToCsvTransformer();
    }

    @Test
    public void transform() {
        genericFileToCsvTransformer = new GenericFileToCsvTransformer();
        String expected = "Birth date,First name,Last name,Weight\r\n" +
                "01/01/1970,John,Smith,81.5\r\n" +
                "31/01/1975,Jane,Doe,61.1\r\n" +
                "28/11/1988,\"Bo'b\",Big,102.4\r\n";
        assertEquals(expected, genericFileToCsvTransformer.transform(genericFileRepresentation));
    }

    @Test(expected = IllegalArgumentException.class)
    public void transformBad() throws InstantiationException, IllegalAccessException {
        // Cant get a bad Generic File Representation easily
        genericFileRepresentationBad.addRow(getGenericFileRowDodgy("1988-11-28", "Bo'b", "Big", "102.4"));
        genericFileToCsvTransformer = new GenericFileToCsvTransformer();
        String expected = "Birth date,First name,Last name,Weight\r\n" +
                "01/01/1970,John,Smith,81.5\r\n" +
                "31/01/1975,Jane,Doe,61.1\r\n" +
                "28/11/1988,\"Bo'b\",Big,102.4\r\n";
        assertEquals(expected, genericFileToCsvTransformer.transform(genericFileRepresentationBad));
    }

    @Test
    public void testGetterSetters() {
        // clean object to guarantee defaults.
        genericFileToCsvTransformer = new GenericFileToCsvTransformer();
        // test defaults
        assertEquals("\r\n", genericFileToCsvTransformer.getEolSeparator());
        assertEquals(",", genericFileToCsvTransformer.getSeparator());
        genericFileToCsvTransformer.setEolSeparator("\n");
        genericFileToCsvTransformer.setSeparator("|");
        assertEquals("\n", genericFileToCsvTransformer.getEolSeparator());
        assertEquals("|", genericFileToCsvTransformer.getSeparator());
    }

    private Path getPath(String aPath) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(aPath).toURI());
    }

    private GenericFileRow createRow(String aBirthDate, String aFirstName, String aLastName, String numberWeight) throws InstantiationException, IllegalAccessException {
        return getGenericFileRow(aBirthDate, aFirstName, aLastName, numberWeight);
    }
}