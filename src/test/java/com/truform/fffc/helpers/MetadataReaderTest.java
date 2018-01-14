package com.truform.fffc.helpers;

import com.truform.fffc.datatypes.Column;
import com.truform.fffc.datatypes.ColumnType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MetadataReaderTest {
    private static String METADATA_COLUMN = "Birth date,10,date";

    @Test
    public void testAddColumnFromLine() {
        ArrayList<Column> senser = new ArrayList<>();

        MetadataReader.addColumnFromLine(senser, METADATA_COLUMN);

        assert senser.size()==1;
        assertEquals(senser.get(0), new Column("Birth date", 10, ColumnType.DATE));
    }
}