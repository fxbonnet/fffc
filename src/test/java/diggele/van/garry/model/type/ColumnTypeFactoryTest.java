package diggele.van.garry.model.type;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ColumnTypeFactoryTest {

    private TypeConverter typeConverter;

    @Test
    public void checkTypeRegistryGood1() {
        try {
            typeConverter = ColumnTypeFactory.getNewInstanceType("date");
            assertTrue(typeConverter instanceof DateTypeConverter);
        } catch (IllegalAccessException | InstantiationException aE) {
            aE.printStackTrace();
        }
    }

    @Test
    public void checkTypeRegistryGood2() {
        try {
            typeConverter = ColumnTypeFactory.getNewInstanceType("string");
            assertTrue(typeConverter instanceof StringTypeConverter);
        } catch (IllegalAccessException | InstantiationException aE) {
            aE.printStackTrace();
        }
    }

    @Test
    public void checkTypeRegistryGood3() {
        try {
            typeConverter = ColumnTypeFactory.getNewInstanceType("numeric");
            assertTrue(typeConverter instanceof NumericTypeConverter);
        } catch (IllegalAccessException | InstantiationException aE) {
            aE.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNewInstanceTypeBad1() {
        try {
            typeConverter = ColumnTypeFactory.getNewInstanceType("bad");
        } catch (IllegalAccessException | InstantiationException aE) {
            aE.printStackTrace();
        }
    }

    @Test
    public void checkTypeRegistryGood() {
        assertTrue(ColumnTypeFactory.checkTypeRegistry("numeric"));
        assertFalse(ColumnTypeFactory.checkTypeRegistry("bad"));
    }
}