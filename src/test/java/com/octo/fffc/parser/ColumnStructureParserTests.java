package com.octo.fffc.parser;


import com.octo.fffc.exception.InvalidStructureException;
import com.octo.fffc.model.ColumnStructure;
import com.octo.fffc.model.ColumnType;
import org.junit.Assert;
import org.junit.Test;

public class ColumnStructureParserTests {

    @Test
    public void testParse() throws InvalidStructureException {

        String columnInfo = "Birth date,10,date";

        ColumnStructure columnStructure = ColumnStructureParser.parse(columnInfo);

        Assert.assertEquals("Birth date", columnStructure.getColumnName());
        Assert.assertEquals(Integer.valueOf(10), columnStructure.getLength());
        Assert.assertEquals(ColumnType.DATE, columnStructure.getType());
    }

    @Test(expected = InvalidStructureException.class)
    public void testParseShouldThrowExceptionIfColumnInfoIsNull() throws InvalidStructureException {
        ColumnStructureParser.parse(null);
    }

    @Test(expected = InvalidStructureException.class)
    public void testParseShouldThrowExceptionIfColumnInfoIsEmptyString() throws InvalidStructureException {
        ColumnStructureParser.parse(" ");
    }

    @Test(expected = InvalidStructureException.class)
    public void testParseShouldThrowExceptionIfColumnInfoDoesNotHaveThreeFields() throws InvalidStructureException {
        ColumnStructureParser.parse("Birth date,10,");
    }

    @Test(expected = InvalidStructureException.class)
    public void testParseShouldThrowExceptionIfColumnDefinitionNameIsEmpty() throws InvalidStructureException {
        ColumnStructureParser.parse(" ,10,date");
    }

    @Test(expected = InvalidStructureException.class)
    public void testParseShouldThrowExceptionIfColumnDefinitionLengthIsEmpty() throws InvalidStructureException {
        ColumnStructureParser.parse("Birth date,,date");
    }

    @Test(expected = InvalidStructureException.class)
    public void testParseShouldThrowExceptionIfColumnDefinitionTypeIsEmpty() throws InvalidStructureException {
        ColumnStructureParser.parse("Birth date,10, ");
    }

    @Test(expected = InvalidStructureException.class)
    public void testParseShouldThrowExceptionIfColumnDefinitionLengthIsNotANumber() throws InvalidStructureException {
        ColumnStructureParser.parse("Birth date,Xx,date");
    }

    @Test(expected = InvalidStructureException.class)
    public void testParseShouldThrowExceptionIfColumnDefinitionLengthIsNotAnInteger() throws InvalidStructureException {
        ColumnStructureParser.parse("Birth date,10.5,date");
    }

    @Test(expected = InvalidStructureException.class)
    public void testParseShouldThrowExceptionIfColumnDefinitionTypeIsNotAValidType() throws InvalidStructureException {
        ColumnStructureParser.parse("Birth date,10.5,color");
    }

}
