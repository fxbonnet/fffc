package com.truform.fffc.datatypes;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NumericTest {
    private static String INPUT = "80";
    private static String OUTPUT = "80";
    private static String NEG_INPUT = "-80";
    private static String NEG_OUTPUT = "-80";
    private static String LEAD_INPUT = " 80";
    private static String LEAD_OUTPUT = "80";
    private static String TRAIL_INPUT = "80 ";
    private static String TRAIL_OUTPUT = "80";
    private static String DEC_INPUT = "80.5";
    private static String DEC_OUTPUT = "80.5";
    private static String ALL_INPUT = " -80.5 ";
    private static String ALL_OUTPUT = "-80.5";

    @Test
    public void testNormal() {
        Numeric sut = new Numeric(INPUT);
        assertThat(sut.toString(), is(OUTPUT));
    }

    @Test
    public void testNegative() {
        Numeric sut = new Numeric(NEG_INPUT);
        assertThat(sut.toString(), is(NEG_OUTPUT));
    }

    @Test
    public void testTrailingWhitespace() {
        Numeric sut = new Numeric(TRAIL_INPUT);
        assertThat(sut.toString(), is(TRAIL_OUTPUT));
    }

    @Test
    public void testLeadingWhitespace() {
        Numeric sut = new Numeric(LEAD_INPUT);
        assertThat(sut.toString(), is(LEAD_OUTPUT));
    }

    @Test
    public void testDecimal() {
        Numeric sut = new Numeric(DEC_INPUT);
        assertThat(sut.toString(), is(DEC_OUTPUT));
    }

    @Test
    public void testAll() {
        Numeric sut = new Numeric(ALL_INPUT);
        assertThat(sut.toString(), is(ALL_OUTPUT));
    }
}