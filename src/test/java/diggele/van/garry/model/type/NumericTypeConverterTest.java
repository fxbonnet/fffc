package diggele.van.garry.model.type;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumericTypeConverterTest {

    private TypeConverter numericTypeConverter;

    @Before
    public void setUp() throws Exception {
        numericTypeConverter = ColumnTypeFactory.getNewInstanceType("numeric");
    }

    @Test
    public void convertGood_1() {
        String testValue = "1.0";
        String expectedValue = "1.0";
        assertEquals(expectedValue, numericTypeConverter.convert(testValue));
    }

    @Test
    public void convertGood_2() {
        String testValue = "100,000.0";
        String expectedValue = "100000.0";
        assertEquals(expectedValue, numericTypeConverter.convert(testValue));
    }

    @Test
    public void convertGood_3() {
        String testValue = "-100,000.0";
        String expectedValue = "-100000.0";
        assertEquals(expectedValue, numericTypeConverter.convert(testValue));
    }

    @Test
    public void convertGood_4() {
        String testValue = "2";
        // confirm a decimal separator is utilised
        String expectedValue = "2.0";
        assertEquals(expectedValue, numericTypeConverter.convert(testValue));
    }

    @Test(expected = NumberFormatException.class)
    public void convertBad_1() {
        String testValue = "-a100,000.0";
        String expectedValue = "-100000.0";
        assertEquals(expectedValue, numericTypeConverter.convert(testValue));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertBad_2() {
        String testValue = null;
        String expectedValue = "-100000.0";
        assertEquals(expectedValue, numericTypeConverter.convert(testValue));
    }

    @Test
    public void toStringTest() {
        String expectedValue = "NumericTypeConverter{}";
        assertEquals(expectedValue, numericTypeConverter.toString());
    }
}