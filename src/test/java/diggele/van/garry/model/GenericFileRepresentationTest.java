package diggele.van.garry.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenericFileRepresentationTest {
    private MetaDataFileDefinition metaDataFileDefinition = new MetaDataFileDefinition();
    private GenericFileRepresentation genericFileRepresentation;
    private Column testColumn1;
    private Column testColumn2;
    private Column testColumn3;

    @Before
    public void setUp() throws Exception {
        testColumn1 = new Column.ColumnBuilder().length(10).name("test1").position(1).type("string").value("bob").createColumn();
        testColumn2 = new Column.ColumnBuilder().length(10).name("test2").position(2).type("date").value("1988-11-28").createColumn();
        testColumn3 = new Column.ColumnBuilder().length(10).name("test3").position(3).type("numeric").value("102.4").createColumn();
        metaDataFileDefinition.addColumnDefinition(testColumn1);
        metaDataFileDefinition.addColumnDefinition(testColumn2);
        metaDataFileDefinition.addColumnDefinition(testColumn3);
        genericFileRepresentation = new GenericFileRepresentation(metaDataFileDefinition);
    }

    @Test
    public void addRow() {
        genericFileRepresentation = new GenericFileRepresentation(metaDataFileDefinition);
        GenericFileRow genericFileRow = new GenericFileRow();
        genericFileRow.addColumn(testColumn1);
        genericFileRow.addColumn(testColumn2);
        genericFileRow.addColumn(testColumn3);
        genericFileRepresentation.addRow(genericFileRow);
        assertEquals(3, genericFileRow.getNumberOfColumns());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addRowBad_1() throws InstantiationException, IllegalAccessException {
        genericFileRepresentation = new GenericFileRepresentation(metaDataFileDefinition);
        GenericFileRow genericFileRow = new GenericFileRow();
        genericFileRow.addColumn(testColumn1);
        genericFileRow.addColumn(new Column.ColumnBuilder().length(10).name("test2").position(1).type("date").value("1988-11-28").createColumn());
        genericFileRow.addColumn(testColumn2);
        assertEquals(1, genericFileRow.getNumberOfColumns());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addRowBad_2() {
        genericFileRepresentation.addRow(new GenericFileRow());
    }

    @Test(expected = RuntimeException.class)
    public void addRowBad_3() throws InstantiationException, IllegalAccessException {
        GenericFileRow genericFileRow = new GenericFileRow();
        genericFileRow.addColumn(testColumn1);
        genericFileRow.addColumn(new Column.ColumnBuilder().length(10).name("test2").position(2).type("string").value("1988-11-28").createColumn());
        genericFileRow.addColumn(testColumn3);

        genericFileRepresentation.addRow(genericFileRow);
    }

    @Test
    public void getMetaDataFileDefinition() {
        genericFileRepresentation = new GenericFileRepresentation(metaDataFileDefinition);
        assertEquals(metaDataFileDefinition, genericFileRepresentation.getMetaDataFileDefinition());
    }

    @Test
    public void getFileRows() {
        genericFileRepresentation = new GenericFileRepresentation(metaDataFileDefinition);
        GenericFileRow genericFileRow = new GenericFileRow();
        genericFileRow.addColumn(testColumn1);
        genericFileRow.addColumn(testColumn2);
        genericFileRow.addColumn(testColumn3);
        genericFileRepresentation.addRow(genericFileRow);

        assertEquals(1, genericFileRepresentation.getFileRows().size());
        genericFileRepresentation.addRow(genericFileRow);
        assertEquals(2, genericFileRepresentation.getFileRows().size());
    }

    @Test
    public void toStringTester() {
        String expected = "GenericFileRepresentation{" +
                "numberOfColumns=" + genericFileRepresentation.getNumberOfColumns() +
                ", metaDataFileDefinition=" + metaDataFileDefinition +
                ", fileRows=" + genericFileRepresentation.getFileRows() +
                '}';
        assertEquals(expected, genericFileRepresentation.toString());
    }
}