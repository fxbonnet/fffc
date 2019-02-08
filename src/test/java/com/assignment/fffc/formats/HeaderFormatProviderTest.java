package com.assignment.fffc.formats;

import com.assignment.fffc.model.Column;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeaderFormatProviderTest {

    public static final String EXPECTED_HEADER = "Birth date,First name,Last name,Weight\n";
    @Autowired
    private HeaderFormatProvider headerFormatProvider;

    private List<Column> columns;

    @Before
    public void setUp() {
        columns = new ArrayList<Column>();
        columns.add(new Column("Birth date", 10, "date"));
        columns.add(new Column("First name", 15, "string"));
        columns.add(new Column("Last name", 15, "string"));
        columns.add(new Column("Weight", 5, "numeric"));

    }

    @Test
    public void addHeader() {
        String headerRow = headerFormatProvider.addHeader("csv", columns);
        assertEquals(EXPECTED_HEADER, headerRow);
    }
}