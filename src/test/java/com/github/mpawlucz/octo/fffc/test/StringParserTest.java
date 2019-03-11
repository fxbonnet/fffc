package com.github.mpawlucz.octo.fffc.test;

import com.github.mpawlucz.octo.fffc.domain.parser.StringColumnParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringParserTest {

    private static final StringColumnParser String_COLUMN_PARSER = StringColumnParser.builder()
            .length(5)
            .name("test string column")
            .build();

    @Test
    public void shouldFormatUnicode() {
        assertEquals(String_COLUMN_PARSER.parse("Asdfɤy\"'"), "Asdfɤy\"'");
    }

    @Test
    public void shouldTrimRight() {
        assertEquals(String_COLUMN_PARSER.parse("   abc   "), "   abc");
    }

}
