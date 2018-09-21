package org.octo.converter;

import org.junit.Test;
import java.text.ParseException;
import static org.junit.Assert.assertArrayEquals;

public class TestDateConverter {

    DateConverter dateConverter = new DateConverter();

    @Test
    public void positiveConvertDate() {
        String[] inputFields = {"1972-01-25","Johan","R","25.5"};
        int[] dateIndex = {0};
        String[] outputFields = {"25/01/1972","Johan","R","25.5"};
        try {
            assertArrayEquals(outputFields, dateConverter.convertDataTypes(inputFields, dateIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noFiledWithDate() {
        String[] inputFields = {"Australia","Johan","R","25.5"};
        int[] dateIndex = {};
        String[] outputFields = {"Australia","Johan","R","25.5"};
        try {
            assertArrayEquals(outputFields, dateConverter.convertDataTypes(inputFields, dateIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ParseException.class)
    public void negativeConvertDate() throws Exception{
        String[] inputFields = {"19720201","Johan","R","25.5"};
        int[] dateIndex = {0};
            dateConverter.convertDataTypes(inputFields, dateIndex);
    }
}
