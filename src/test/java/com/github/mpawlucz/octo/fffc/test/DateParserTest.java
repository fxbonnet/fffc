package com.github.mpawlucz.octo.fffc.test;

import com.github.mpawlucz.octo.fffc.domain.parser.DateColumnParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateParserTest {

    private static final DateColumnParser DATE_COLUMN_PARSER = DateColumnParser.builder()
            .length(5)
            .name("test date column")
            .build();

    @Test
    public void shouldFormatCorrectDate() {
        assertEquals(DATE_COLUMN_PARSER.parse("1992-01-31"), "31/01/1992");
    }

    @Test
    public void shouldFailForIncorrectDate() {
        assertThrows(RuntimeException.class, () -> {
            DATE_COLUMN_PARSER.parse("1992-02-30");
        });
    }

    @Test
    public void shouldFailForIncompleteDate() {
        assertThrows(RuntimeException.class, () -> {
            DATE_COLUMN_PARSER.parse("2008-1");
        });
    }

}
