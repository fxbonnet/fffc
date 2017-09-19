package fffc.entities;

import fffc.Exceptions.InvalidFieldFormatException;
import fffc.enums.Type;
import org.junit.Assert;
import org.junit.Test;

public class DateFieldTest {

    @Test
    public void testValidDateFormat() throws Exception {

        String dataLine = "1970-01-01John           Smith           81.5";
        FieldMetaData fmd = new FieldMetaData();
        fmd.setLength(10);
        fmd.setType(Type.DATE);

        Field date = Field.create(dataLine,0,fmd);

        Assert.assertNotNull(date);
        Assert.assertTrue(date instanceof DateField);
        Assert.assertEquals("01/01/1970", date.getFormatted());
    }


    @Test(expected = InvalidFieldFormatException.class)
    public void testInValidDateFormat() throws Exception {

        String dataLine = "1970/01-01John           Smith           81.5";
        FieldMetaData fmd = new FieldMetaData();
        fmd.setLength(10);
        fmd.setType(Type.DATE);

        Field date = Field.create(dataLine,0,fmd);
    }

}