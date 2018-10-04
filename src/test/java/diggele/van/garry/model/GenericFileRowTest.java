package diggele.van.garry.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GenericFileRowTest {

    private Column testColumn1;
    private Column testColumn2;
    private Column testColumn3;
    private GenericFileRow genericFileRow = new GenericFileRow();

    @Before
    public void setUp() throws Exception {
        testColumn1 = new Column.ColumnBuilder().length(10).name("test1").position(1).type("string").value("bob").createColumn();
        testColumn2 = new Column.ColumnBuilder().length(10).name("test2").position(2).type("date").value("1988-11-28").createColumn();
        testColumn3 = new Column.ColumnBuilder().length(10).name("test3").position(3).type("numeric").value("102.4").createColumn();
    }

    @Test
    public void addColumn() {
        genericFileRow = new GenericFileRow();
        genericFileRow.addColumn(testColumn1);
        assertEquals(1, genericFileRow.getNumberOfColumns());
        genericFileRow.addColumn(testColumn2);
        assertEquals(2, genericFileRow.getNumberOfColumns());
    }

    @Test
    public void getColumnByPosition() {
        genericFileRow = new GenericFileRow();
        genericFileRow.addColumn(testColumn1);
        genericFileRow.addColumn(testColumn2);
        genericFileRow.addColumn(testColumn3);
        assertEquals(testColumn1, genericFileRow.getColumnByPosition(testColumn1));
        assertEquals(testColumn2, genericFileRow.getColumnByPosition(2));
    }

    @Test
    public void getColumnValue() {
        genericFileRow = new GenericFileRow();
        genericFileRow.addColumn(testColumn1);
        genericFileRow.addColumn(testColumn2);
        genericFileRow.addColumn(testColumn3);
        assertEquals(testColumn1.getValue(), genericFileRow.getColumnValue(testColumn1));
        assertNotEquals(testColumn1.getValue(), genericFileRow.getColumnValue(testColumn2));
    }

    @Test
    public void toStringTester() {
        HashMap<String, Column> columns = new HashMap<>();

        genericFileRow = new GenericFileRow();
        genericFileRow.addColumn(testColumn1);
        genericFileRow.addColumn(testColumn2);
        genericFileRow.addColumn(testColumn3);
        columns.put(String.valueOf(testColumn1.getPosition()), testColumn1);
        columns.put(String.valueOf(testColumn2.getPosition()), testColumn2);
        columns.put(String.valueOf(testColumn3.getPosition()), testColumn3);
        String expected = "GenericFileRow{" +
                "columns=" + columns +
                '}';
        assertEquals(expected, genericFileRow.toString());
    }
}