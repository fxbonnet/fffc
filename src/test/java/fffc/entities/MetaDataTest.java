package fffc.entities;

import fffc.entities.MetaData;
import org.junit.Test;

import java.io.IOException;

import static fffc.enums.Type.DATE;
import static fffc.enums.Type.NUMERIC;
import static org.junit.Assert.*;


public class MetaDataTest {

    @Test
    public void testGetHealthyMetaData () throws IOException {
        MetaData md = new MetaData("src/test/resources/metadata.txt");

        assertEquals(4, md.getMetaData().size());

        assertEquals("Birth date" , md.getMetaData().get(0).getTitle());
        assertEquals( "First name", md.getMetaData().get(1).getTitle());
        assertEquals( 15, md.getMetaData().get(2).getLength());
        assertEquals( 5, md.getMetaData().get(3).getLength());
        assertEquals( DATE, md.getMetaData().get(0).getType());
        assertEquals( NUMERIC, md.getMetaData().get(3).getType());

    }

    @Test
    public void testAsCsv() throws Exception {
        MetaData md = new MetaData("src/test/resources/metadata.txt");

        assertEquals("Birth date,First name,Last name,Weight", md.asCsv());
    }
}