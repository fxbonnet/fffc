package fffc.entities;

import fffc.enums.Type;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringFieldTest {

    @Test
    public void testGetFormattedNoSpecialCharacter() throws Exception {
        String dataLine = "1970-01-01John           Smith           81.5";
        FieldMetaData fmd = new FieldMetaData();
        fmd.setLength(15);
        fmd.setType(Type.STRING);

        Field field = Field.create(dataLine,10,fmd);

        Assert.assertNotNull(field);
        Assert.assertTrue(field instanceof StringField);
        Assert.assertEquals("John", field.getFormatted());
    }

    @Test
    public void testGetFormattedWithSpecialCharacter() throws Exception {
        String dataLine = "1970-01-01Jo,n           Smith           81.5";
        FieldMetaData fmd = new FieldMetaData();
        fmd.setLength(15);
        fmd.setType(Type.STRING);

        Field field = Field.create(dataLine,10,fmd);

        Assert.assertNotNull(field);
        Assert.assertTrue(field instanceof StringField);
        Assert.assertEquals("\"Jo,n\"", field.getFormatted());
    }

}