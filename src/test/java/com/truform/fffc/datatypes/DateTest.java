package com.truform.fffc.datatypes;

import com.truform.fffc.exceptions.DateException;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DateTest {
    private static String CORRECT = "1990-02-01";
    private static String INCORRECT = "01-02-1990";
    private static String RESULT = "01/02/1990";

    @Test
    public void testCreatePos() {
        Date sut = new Date(CORRECT);
        assertThat(sut.toString(), is(RESULT));
    }

    @Test(expected = DateException.class)
    public void testCreateNeg() {
        Date sut = new Date(INCORRECT);
    }
}