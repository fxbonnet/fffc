package com.mrdaydreamer.model;

import org.junit.Test;

import junit.framework.Assert;

public class DataRecordTest {
	
	private static final String EMPTY_STR = "";
	private static final String FIELD1_STR = "Field1";
	private static final String FIELD2_STR = "Field2";
	private static final String FIELD_SEPERATOR_STR = ",";
	

	@Test
	public void TestAppendAndPrint() {
		DataRecord record = new DataRecord();
		Assert.assertEquals(EMPTY_STR, record.print());
		
		record.appendField(FIELD1_STR);
		Assert.assertEquals(FIELD1_STR, record.print());
		
		record.appendField(FIELD2_STR);
		Assert.assertEquals(FIELD1_STR + FIELD_SEPERATOR_STR + FIELD2_STR, record.print());
	}
	
	@Test
	public void TestSize() {
		DataRecord record = new DataRecord();
		Assert.assertEquals(0, record.size());
		
		record.appendField(FIELD1_STR);
		Assert.assertEquals(1, record.size());
		
		record.appendField(FIELD2_STR);
		Assert.assertEquals(2, record.size());
	}
}
