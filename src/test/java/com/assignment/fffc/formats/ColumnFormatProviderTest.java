package com.assignment.fffc.formats;

import com.assignment.fffc.model.ColumnType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ColumnFormatProviderTest {

    @Autowired
    private ColumnFormatProvider columnFormatProvider;

    @Test
    public void shouldformatColumnBasedOnType() {

        Function<String, Object> format = columnFormatProvider.format(ColumnType.STRING.toString());
        assertEquals("\"Smith,a\"", format.apply("Smith,a  "));
        format = columnFormatProvider.format(ColumnType.NUMERIC.toString());
        assertEquals(16.5, format.apply("16.5"));
        format = columnFormatProvider.format(ColumnType.DATE.toString());
        assertEquals("01/01/1970", format.apply("1970-01-01"));
    }


}