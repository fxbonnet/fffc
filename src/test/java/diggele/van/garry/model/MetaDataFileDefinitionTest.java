package diggele.van.garry.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MetaDataFileDefinitionTest {
    private MetaDataFileDefinition metaDataFileDefinition = new MetaDataFileDefinition();
    private Column testColumn1;
    private Column testColumn2;
    private Column testColumn3;

    @Before
    public void setUp() throws Exception {
        testColumn1 = new Column.ColumnBuilder().length(10).name("test1").position(1).type("string").value("bob").createColumn();
        testColumn2 = new Column.ColumnBuilder().length(10).name("test2").position(2).type("date").value("1988-11-28").createColumn();
        testColumn3 = new Column.ColumnBuilder().length(10).name("test3").position(3).type("numeric").value("102.4").createColumn();
    }

    @Test
    public void addColumnDefinition() {
        metaDataFileDefinition = new MetaDataFileDefinition();
        metaDataFileDefinition.addColumnDefinition(testColumn1);
        assertEquals(1, metaDataFileDefinition.getDefinitions().size());
    }

    @Test(expected = NullPointerException.class)
    public void addColumnDefinitionNullCheck() {
        metaDataFileDefinition = new MetaDataFileDefinition();
        metaDataFileDefinition.addColumnDefinition(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addColumnDefinitionExistCheck() {
        metaDataFileDefinition = new MetaDataFileDefinition();
        metaDataFileDefinition.addColumnDefinition(testColumn1);
        metaDataFileDefinition.addColumnDefinition(new Column.ColumnBuilder().createColumnClone(testColumn1));
    }

    @Test
    public void removeColumnDefinition() {
        metaDataFileDefinition = new MetaDataFileDefinition();
        metaDataFileDefinition.addColumnDefinition(testColumn1);
        metaDataFileDefinition.addColumnDefinition(testColumn2);
        metaDataFileDefinition.removeColumnDefinition(testColumn1);
        assertEquals(1, metaDataFileDefinition.getNumberOfColumns());
        assertNotEquals(testColumn1, metaDataFileDefinition.getColumnAtPosition(1));
    }
}