package octo.util;


import octo.model.ColumnType;
import org.junit.Assert;
import org.junit.Test;

import java.time.format.DateTimeParseException;

public class DataFormatterTest {

    @Test
    public void testFormatStringWithTrailingSpaces() {
        String input = "John      ";
        String expected = "John";
        Assert.assertEquals(expected, DataFormatter.formatString(input));
    }

    @Test
    public void testFormatString() {
        String input = null;
        String expected = null;
        Assert.assertEquals(expected, DataFormatter.formatString(input));

        input = "";
        expected = "";
        Assert.assertEquals(expected, DataFormatter.formatString(input));

        input = "            ";
        expected = "";
        Assert.assertEquals(expected, DataFormatter.formatString(input));
    }

    @Test
    public void testFormatStringWithComma() {
        String input = "John,Doe  ";
        String expected = "\"John,Doe\"";
        Assert.assertEquals(expected, DataFormatter.formatString(input));
    }

    @Test
    public void testFormatDate() {
        String input = "1980-12-01      ";
        String expected = "01/12/1980";
        Assert.assertEquals(expected, DataFormatter.formatDate(input));

        input = null;
        expected = null;
        Assert.assertEquals(expected, DataFormatter.formatDate(input));

        input = "";
        expected = "";
        Assert.assertEquals(expected, DataFormatter.formatDate(input));

        input = "     ";
        expected = "     ";
        Assert.assertEquals(expected, DataFormatter.formatDate(input));
    }

    @Test(expected = DateTimeParseException.class)
    public void testFormatInvalidDate() {
        String input = "1980-15-01";
        DataFormatter.formatDate(input);
    }

    @Test
    public void testFormatNumber() {
        String input = "0";
        String expected = "0";
        Assert.assertEquals(expected, DataFormatter.formatString(input));

        input = "1.12345";
        expected = "1.12345";
        Assert.assertEquals(expected, DataFormatter.formatString(input));

        input = "  1234.1234    ";
        expected = "1234.1234";
        Assert.assertEquals(expected, DataFormatter.formatString(input));

        input = "-12 ";
        expected = "-12";
        Assert.assertEquals(expected, DataFormatter.formatString(input));
    }

    @Test
    public void testFormatData() {
        ColumnType type = ColumnType.STRING;
        String input = "John,Doe";
        String expected = "\"John,Doe\"";
        Assert.assertEquals(expected, DataFormatter.formatData(input, type));

        type = ColumnType.DATE;
        input = "1980-12-01";
        expected = "01/12/1980";
        Assert.assertEquals(expected, DataFormatter.formatData(input, type));

        type = ColumnType.NUMERIC;
        input = " 11  ";
        expected = "11";
        Assert.assertEquals(expected, DataFormatter.formatData(input, type));
    }

}
