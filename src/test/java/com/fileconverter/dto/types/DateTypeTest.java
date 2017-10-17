package com.fileconverter.dto.types;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fileconverter.util.BLException;

public class DateTypeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIfParsedDate() throws BLException {
		DateType date = new DateType();
		String result = date.parse("1917-01-31");
		assertEquals("31/01/1917", result);
	}
	@Test(expected=BLException.class)
	public void testIfNotParsedIncorrectDate() throws BLException {
		DateType date = new DateType();
		String result = date.parse("1917/01/31");
		assertEquals("31/01/1917", result);
	}

}
