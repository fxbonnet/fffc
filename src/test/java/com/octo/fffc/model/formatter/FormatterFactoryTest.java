package com.octo.fffc.model.formatter;

import com.octo.fffc.exceptions.FormatterException;
import org.junit.Test;

import java.util.function.Function;

import static com.octo.fffc.model.formatter.FormatterFactory.formatter;
import static com.octo.fffc.model.metadata.DataType.DATE;
import static com.octo.fffc.model.metadata.DataType.STRING;
import static org.junit.Assert.assertEquals;

public class FormatterFactoryTest {

    @Test
    public void testDateFormatter() {
        String input = "1970-01-01";
        Function<String, String> formatter = formatter(DATE);
        assertEquals("01/01/1970", formatter.apply(input));
    }

    @Test(expected = FormatterException.class)
    public void testDateFormatterInvalidDateFormat() {
        String input = "01-01-1970";
        Function<String, String> formatter = formatter(DATE);
        assertEquals("01/01/1970", formatter.apply(input));
    }

    @Test(expected = FormatterException.class)
    public void testDateFormatterNullInput() {
        Function<String, String> formatter = formatter(DATE);
        formatter.apply(null);
    }

    @Test(expected = FormatterException.class)
    public void testDateFormatterEmptyInput() {
        Function<String, String> formatter = formatter(DATE);
        formatter.apply("");
    }

    @Test
    public void testFormatterForStringDataType() {
        Function<String, String> formatter = formatter(STRING);
        String input = "John     ";
        assertEquals("John", formatter.apply(input));
    }

    @Test
    public void testFormatterForStringDataTypeWithEmptyInput() {
        Function<String, String> formatter = formatter(STRING);
        String input = "     ";
        assertEquals("", formatter.apply(input));
    }

    @Test (expected = FormatterException.class)
    public void testFormatterForStringDataTypeWithNullInput() {
        Function<String, String> formatter = formatter(STRING);
        assertEquals("", formatter.apply(null));
    }
}