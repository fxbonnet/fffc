package com.octo.fffc.formatter;

import org.junit.Test;

import java.util.Optional;

import static com.octo.fffc.formatter.FormatterUtils.convertDate;
import static com.octo.fffc.formatter.FormatterUtils.isValidNumeric;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFormatterUtils {

    @Test
    public void testDateFormatConversionWithValidInput() {
        String inputFormat = "yyyy-mm-dd";
        String outputFormat = "dd/mm/yyyy";
        Optional<String> actualOutput = convertDate("1970-01-01", inputFormat, outputFormat);
        assertTrue(actualOutput.isPresent() && actualOutput.get().equals("01/01/1970"));
    }

    @Test
    public void testDateFormatWithInvalidDate() {
        String inputFormat = "yyyy-mm-dd";
        String outputFormat = "dd/mm/yyyy";
        Optional<String> actualOutput = convertDate("1970/01/01", inputFormat, outputFormat);
        assertFalse(actualOutput.isPresent());
    }

    @Test
    public void testIsValidNumericWithValidInput() {
        assertTrue(isValidNumeric("-10.2321"));
    }

    @Test
    public void testIsValidNumbericWithInvalidInput() {
        assertFalse(isValidNumeric("10.232.1232.0"));
    }
}
