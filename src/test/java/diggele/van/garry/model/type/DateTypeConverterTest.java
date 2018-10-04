package diggele.van.garry.model.type;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DateTypeConverterTest {

    private final String inputPatternDefault = "yyyy-mm-dd";
    private final String outputPatternDefault = "dd/mm/yyyy";
    private final String testValue1 = "1970-01-01";
    private final String testValue2 = "1975-01-31";
    private final String testValue3 = "1988-11-28";
    private TypeConverter dateTypeConverter;

    @Before
    public void setUp() throws Exception {
        dateTypeConverter = ColumnTypeFactory.getNewInstanceType("date");
    }


    @Test
    public void convertGood_1() {
        String expectedValue1 = "01/01/1970";
        String expectedValue2 = "31/01/1975";
        String expectedValue3 = "28/11/1988";

        String testValue4 = "1879-12-01";
        String expectedValue4 = "01/12/1879";
        assertEquals(expectedValue1, dateTypeConverter.convert(testValue1));
        assertEquals(expectedValue2, dateTypeConverter.convert(testValue2));
        assertEquals(expectedValue3, dateTypeConverter.convert(testValue3));
        assertEquals(expectedValue4, dateTypeConverter.convert(testValue4));
    }

    @Test
    public void convertBad_1() {
        String expectedValue1 = "1/01/1970";
        String expectedValue2 = "01/31/1975";
        String expectedValue3 = "1988/11/28";

        String testValue4 = "1879-12-01";
        String expectedValue4 = "1/12/1879";
        assertNotEquals(expectedValue1, dateTypeConverter.convert(testValue1));
        assertNotEquals(expectedValue2, dateTypeConverter.convert(testValue2));
        assertNotEquals(expectedValue3, dateTypeConverter.convert(testValue3));
        assertNotEquals(expectedValue4, dateTypeConverter.convert(testValue4));
    }

    @Test
    public void convertBad_2() {
        String testValue4 = "1879-12-01";
        String expectedValue4 = "1/12/1879";
        assertNotEquals(expectedValue4, dateTypeConverter.convert(testValue4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertBad_3() {
        String testValue4 = "1879-DEC-01";
        String expectedValue4 = "01/12/1879";
        assertNotEquals(expectedValue4, dateTypeConverter.convert(testValue4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertBad_4() {
        String testValue4 = null;
        String expectedValue4 = "01/12/1879";
        assertNotEquals(expectedValue4, dateTypeConverter.convert(testValue4));
    }

    @Test
    public void testInputPattern() {
        dateTypeConverter = new DateTypeConverter();
        assertEquals(inputPatternDefault, ((DateTypeConverter) dateTypeConverter).getInputPattern());
        ((DateTypeConverter) dateTypeConverter).setInputPattern(outputPatternDefault);
        assertNotEquals(inputPatternDefault, ((DateTypeConverter) dateTypeConverter).getInputPattern());
        assertEquals(outputPatternDefault, ((DateTypeConverter) dateTypeConverter).getInputPattern());
        dateTypeConverter = new DateTypeConverter();
    }

    @Test
    public void testOutputPattern() {
        dateTypeConverter = new DateTypeConverter();
        assertEquals(outputPatternDefault, ((DateTypeConverter) dateTypeConverter).getOutputPattern());
        ((DateTypeConverter) dateTypeConverter).setOutputPattern(inputPatternDefault);
        assertNotEquals(outputPatternDefault, ((DateTypeConverter) dateTypeConverter).getOutputPattern());
        assertEquals(inputPatternDefault, ((DateTypeConverter) dateTypeConverter).getOutputPattern());
        dateTypeConverter = new DateTypeConverter();
    }

    @Test
    public void toStringTest() {
        dateTypeConverter = new DateTypeConverter();
        String expected = "DateTypeConverter{" +
                "inputPattern='" + inputPatternDefault + '\'' +
                ", outputPattern='" + outputPatternDefault + '\'' +
                '}';
        assertEquals(expected, dateTypeConverter.toString());
    }
}