package fffc.entities;

import fffc.Exceptions.InvalidFieldFormatException;
import fffc.enums.Type;
import org.junit.Assert;
import org.junit.Test;

public class NumericFieldTest {

    @Test
    public void testValidNumericField() throws Exception {
        String dataLine = "1970-01-01John           Smith           81.5";
        FieldMetaData fmd = new FieldMetaData();
        fmd.setLength(5);
        fmd.setType(Type.NUMERIC);

        Field field = Field.create(dataLine,40,fmd);

        Assert.assertNotNull(field);
        Assert.assertTrue(field instanceof NumericField);
        Assert.assertEquals("81.5", field.getFormatted());
    }

    @Test(expected = InvalidFieldFormatException.class)
    public void testInvalidNumericFieldNoDec() throws Exception {
        String dataLine = "1970-01-01John           Smith           8105";
        FieldMetaData fmd = new FieldMetaData();
        fmd.setLength(5);
        fmd.setType(Type.NUMERIC);

        Field field = Field.create(dataLine,40,fmd);
    }


    @Test(expected = InvalidFieldFormatException.class)
    public void testInvalidNumericFieldLetter() throws Exception {
        String dataLine = "1970-01-01John           Smith           81a5";
        FieldMetaData fmd = new FieldMetaData();
        fmd.setLength(5);
        fmd.setType(Type.NUMERIC);

        Field field = Field.create(dataLine,40,fmd);
    }

}