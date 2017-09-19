package fffc.service;

import fffc.Exceptions.InvalidLineFormatException;
import fffc.entities.MetaData;
import fffc.service.Transformer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransformerTest {

    MetaData md;

    @Before
    public void setUp() throws Exception {
        md = new MetaData("src/test/resources/metadata.txt");
    }

    @Test
    public void testTransformLine1() throws Exception {

        String line = "1970-01-01John           Smith           81.5";

        assertEquals("01/01/1970,John,Smith,81.5", Transformer.transform(line,md));
    }

    @Test
    public void testTransformLine2() throws Exception {

        String line = "1975-01-31Jane           Doe             61.1";

        assertEquals("31/01/1975,Jane,Doe,61.1",Transformer.transform(line,md));
    }

    @Test(expected = InvalidLineFormatException.class)
    public void testTransformFieldException() throws Exception {

        String line = "1975-01-31Ja";

        Transformer.transform(line,md);
    }
}