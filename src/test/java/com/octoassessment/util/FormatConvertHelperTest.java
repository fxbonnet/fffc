package com.octoassessment.util;


import com.octoassessment.model.ColumnMetaData;
import com.octoassessment.model.ColumnType;
import com.octoassessment.model.Line;
import com.octoassessment.model.Metadata;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Arrays;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FormatConvertHelperTest {

    @Mock
    private Metadata metadata;

    @BeforeTest
    private void init() {
        MockitoAnnotations.initMocks(this);
        ColumnMetaData firstNameMetaData = mock(ColumnMetaData.class);
        when(firstNameMetaData.getColumnName()).thenReturn("firstName");
        when(firstNameMetaData.getColumnLength()).thenReturn(15);
        when(firstNameMetaData.getColumnType()).thenReturn(ColumnType.STRING);

        ColumnMetaData lastNameMetaData = mock(ColumnMetaData.class);
        when(lastNameMetaData.getColumnName()).thenReturn("lastName");
        when(lastNameMetaData.getColumnLength()).thenReturn(15);
        when(lastNameMetaData.getColumnType()).thenReturn(ColumnType.STRING);

        ColumnMetaData dateOfBirthMetaData = mock(ColumnMetaData.class);
        when(dateOfBirthMetaData.getColumnName()).thenReturn("DOB");
        when(dateOfBirthMetaData.getColumnLength()).thenReturn(10);
        when(dateOfBirthMetaData.getColumnType()).thenReturn(ColumnType.DATE);

        ColumnMetaData weightMetaData = mock(ColumnMetaData.class);
        when(weightMetaData.getColumnName()).thenReturn("weight");
        when(weightMetaData.getColumnLength()).thenReturn(5);
        when(weightMetaData.getColumnType()).thenReturn(ColumnType.NUMERIC);

        when(metadata.getColumnMetaData()).thenReturn(Arrays.asList(dateOfBirthMetaData, firstNameMetaData, lastNameMetaData, weightMetaData));
    }

    @Test
    public void toLineTestPositive() {
        Line line = FormatConvertHelper.toLine("1970-01-01John           Smith           81.5", metadata);
        Assert.assertEquals(line.getColumns().get(0).getValue(), "01/01/1970");
        Assert.assertEquals(line.getColumns().get(1).getValue(), "John");
        Assert.assertEquals(line.getColumns().get(2).getValue(), "Smith");
        Assert.assertEquals(line.getColumns().get(3).getValue(), "81.5");
    }

    @Test
    public void toLineTestNegativeBadDate() {
        Line line = FormatConvertHelper.toLine("1970-14-01John           Smith           81.5", metadata);
        Assert.assertNull(line);
    }

    @Test
    public void toLineTestPositiveCommaInInput() {
        Line line = FormatConvertHelper.toLine("1970-01-01Jo,h,n         Smith           81.5", metadata);
        Assert.assertEquals(line.getColumns().get(0).getValue(), "01/01/1970");
        Assert.assertEquals(line.getColumns().get(1).getValue(), "\"Jo,h,n\"");
        Assert.assertEquals(line.getColumns().get(2).getValue(), "Smith");
        Assert.assertEquals(line.getColumns().get(3).getValue(), "81.5");
    }

}
