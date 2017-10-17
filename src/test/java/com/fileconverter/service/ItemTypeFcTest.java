package com.fileconverter.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fileconverter.dto.types.DateType;
import com.fileconverter.dto.types.ItemType;
import com.fileconverter.dto.types.NumberType;
import com.fileconverter.dto.types.StringType;
import com.fileconverter.util.BLException;

public class ItemTypeFcTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIfCorrectTypeIsReturned() throws BLException {
		ItemType item = ItemTypeFc.getType("String ");
		assertTrue("expected object is StringType", (item instanceof StringType));
		item = ItemTypeFc.getType("Date ");
		assertTrue("expected object is DateType", (item instanceof DateType));
		item = ItemTypeFc.getType(" numeric ");
		assertTrue("expected object is NumberType", (item instanceof NumberType));
	}
	
	@Test(expected=BLException.class)
	public void testIfNotAllowedUnsupportedType() throws BLException {
		ItemType item = ItemTypeFc.getType("String1 ");
		fail("Should not reach this point"+item.toString());
	}

}
