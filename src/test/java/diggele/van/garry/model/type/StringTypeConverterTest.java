package diggele.van.garry.model.type;

import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StringTypeConverterTest {

    private StringTypeConverter stringTypeConverter;
    // default special characters are everything thats not a number or letter incl space:
    private Pattern defaultPattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);

    @Before
    public void setUp() {
        stringTypeConverter = new StringTypeConverter();
    }

    @Test
    public void convertGood_1() {
        String input = "AbcDay";
        String expected = "AbcDay";
        assertEquals(expected, stringTypeConverter.convert(input));
    }

    @Test
    public void convertGood_2() {
        String input = "AbcDay123";
        String expected = "AbcDay123";
        assertEquals(expected, stringTypeConverter.convert(input));
    }

    @Test
    public void convertGood_3() {
        String input = "AbcD'ay";
        String expected = "\"AbcD'ay\"";
        assertEquals(expected, stringTypeConverter.convert(input));
    }

    @Test
    public void convertGood_4() {
        String input = "Abc Day";
        String expected = "\"Abc Day\"";
        assertEquals(expected, stringTypeConverter.convert(input));
    }

    @Test
    public void convertGood_5() {
        String input = "Abc\tDay^";
        String expected = "\"Abc\tDay^\"";
        assertEquals(expected, stringTypeConverter.convert(input));
    }

    @Test
    public void convertBad_1() {
        String input = "Abc\tDay^";
        String expected = "Abc\tDay^";
        assertNotEquals(expected, stringTypeConverter.convert(input));
    }

    @Test
    public void convertBad_2() {
        String input = "Abc\tDay^";
        String expected = "Abc\tDay^";
        assertNotEquals(expected, stringTypeConverter.convert(input));
    }

    @Test
    public void testPatternSetting() {
        stringTypeConverter = new StringTypeConverter();
        // got funny behaviour comparing patterns directly, work around toString().
        assertEquals(defaultPattern.toString(), stringTypeConverter.getPattern().toString());
        Pattern testPattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        stringTypeConverter.setPattern(testPattern);
        // The default pattern
        assertEquals(testPattern, stringTypeConverter.getPattern());
        stringTypeConverter = new StringTypeConverter();
    }

    @Test
    public void testToString() {
        stringTypeConverter = new StringTypeConverter();
        String expected = "StringTypeConverter{" +
                "pattern=" + defaultPattern +
                '}';
        assertEquals(expected, stringTypeConverter.toString());
    }
}