package com.github.mpawlucz.octo.fffc.test;

import com.github.mpawlucz.octo.fffc.domain.parser.NumericColumnParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumericParserTest {

    private static final NumericColumnParser NUMERIC_COLUMN_PARSER = NumericColumnParser.builder()
            .length(5)
            .name("test numeric column")
            .build();

    @Test
    public void shouldFormatTrimmed(){
        assertEquals(NUMERIC_COLUMN_PARSER.parse("   12.3992  "), "12.3992");
    }

    @Test
    public void shouldTrimLeadingZeroes(){
        assertEquals(NUMERIC_COLUMN_PARSER.parse("00023.33"), "23.33");
    }

    @Test
    public void shouldFormatNegativeNumber(){
        assertEquals(NUMERIC_COLUMN_PARSER.parse("-61.1"), "-61.1");
    }

    @Test
    public void shouldFormatNegativeZero(){
        assertEquals(NUMERIC_COLUMN_PARSER.parse("-0"), "0");
    }

    @Test
    public void shouldFailForNonNumeric(){
        assertThrows(RuntimeException.class, () -> {
            NUMERIC_COLUMN_PARSER.parse("1243a");
        });
    }

}
