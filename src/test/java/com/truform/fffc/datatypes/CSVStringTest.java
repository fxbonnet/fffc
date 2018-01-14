package com.truform.fffc.datatypes;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CSVStringTest {
    private static String SEP_INPUT = "Some,String";
    private static String SEP_OUTPUT = "\"Some,String\"";
    private static String INPUT = "SomeString";
    private static String OUTPUT = "SomeString";
    private static String TRAIL_INPUT = "SomeString   ";
    private static String TRAIL_OUTPUT = "SomeString";
    private static String LEAD_INPUT = "  SomeString";
    private static String LEAD_OUTPUT = "  SomeString";

    @Test
    public void testSep() {
        CSVString sut = new CSVString(SEP_INPUT);
        assertThat(sut.toString(), is(SEP_OUTPUT));
    }

    @Test
    public void testNormal() {
        CSVString sut = new CSVString(INPUT);
        assertThat(sut.toString(), is(OUTPUT));
    }

    @Test
    public void testTrail() {
        CSVString sut = new CSVString(TRAIL_INPUT);
        assertThat(sut.toString(), is(TRAIL_OUTPUT));
    }

    @Test
    public void testLead() {
        CSVString sut = new CSVString(LEAD_INPUT);
        assertThat(sut.toString(), is(LEAD_OUTPUT));
    }
}