package com.an.model;

import org.junit.Assert;
import org.junit.Test;

public class ColumnTypeTest {

    @Test
    public void testGetValue(){

        Assert.assertEquals(ColumnType.DATE,ColumnType.forValue("date"));

        Assert.assertEquals(ColumnType.NUMERIC,ColumnType.forValue("numeric"));

        Assert.assertEquals(ColumnType.STRING,ColumnType.forValue("string"));

    }
}
