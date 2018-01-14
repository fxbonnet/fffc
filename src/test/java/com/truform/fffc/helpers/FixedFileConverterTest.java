package com.truform.fffc.helpers;

import com.truform.fffc.datatypes.Column;
import com.truform.fffc.datatypes.ColumnType;
import com.truform.fffc.datatypes.Metadata;
import com.truform.fffc.exceptions.DateException;
import com.truform.fffc.exceptions.InvalidLineException;
import com.truform.fffc.exceptions.NumericException;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class FixedFileConverterTest {

    private static String CORRECT_HEADER = "First,Second,Third";

    private static String INPUT_LINE = "1970-02-01 -81.5John           ";
    private static String INPUT_BROKEN_1 = "1970-02/01 -81.5John           ";
    private static String INPUT_BROKEN_2 = "1970-02-01 -8a.5John           ";
    private static String INPUT_BROKEN_3 = "1970-02-01 -81.5John          ";
    private static String INPUT_BROKEN_4 = "1970-02-01 -81.5John            ";

    private static String OUTPUT_LINE = "01/02/1970,-81.5,John";

    private static Metadata getIncorrectMetadata() {
        ArrayList<Column> columns = new ArrayList<>();

        columns.add(new Column("Second", 10, ColumnType.DATE));
        columns.add(new Column("First", 6, ColumnType.NUMERIC));
        columns.add(new Column("Third", 15, ColumnType.STRING));

        return new Metadata(columns);
    }

    private static Metadata getCorrectMetadata() {
        ArrayList<Column> columns = new ArrayList<>();

        columns.add(new Column("First", 10, ColumnType.DATE));
        columns.add(new Column("Second", 6, ColumnType.NUMERIC));
        columns.add(new Column("Third", 15, ColumnType.STRING));

        return new Metadata(columns);
    }

    @Test
    public void testGetHeaderPos() {
        String built = FixedFileConverter.getHeader(getCorrectMetadata());
        assertThat(built, is(CORRECT_HEADER));
    }

    @Test
    public void testGetHeaderNeg() {
        String built = FixedFileConverter.getHeader(getIncorrectMetadata());
        assertThat(built, not(CORRECT_HEADER));
    }

    @Test
    public void testConvertLinePos() {
        String built = FixedFileConverter.convertLine(INPUT_LINE, getCorrectMetadata());
        assertThat(built, is(OUTPUT_LINE));
    }

    @Test(expected = DateException.class)
    public void testConvertLineNeg1() {
        String built = FixedFileConverter.convertLine(INPUT_BROKEN_1, getCorrectMetadata());
        assertThat(built, is(OUTPUT_LINE));
    }

    @Test(expected = NumericException.class)
    public void testConvertLineNeg2() {
        String built = FixedFileConverter.convertLine(INPUT_BROKEN_2, getCorrectMetadata());
        assertThat(built, is(OUTPUT_LINE));
    }

    @Test(expected = InvalidLineException.class)
    public void testConvertLineNeg3() {
        String built = FixedFileConverter.convertLine(INPUT_BROKEN_3, getCorrectMetadata());
        assertThat(built, is(OUTPUT_LINE));
    }

    @Test(expected = InvalidLineException.class)
    public void testConvertLineNeg4() {
        String built = FixedFileConverter.convertLine(INPUT_BROKEN_4, getCorrectMetadata());
        assertThat(built, is(OUTPUT_LINE));
    }
}