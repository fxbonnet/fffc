package com.fileconverter.dto.types;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fileconverter.util.BLException;

public class StringTypeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIfParsedString() throws BLException {
		StringType type = new StringType();
		String result = type.parse("-100");
		assertEquals("-100", result);
		result = type.parse("1,00");
		assertEquals("\"1,00\"", result);
		result = type.parse("  01");
		assertEquals("  01", result);
		result = type.parse("01  ");
		assertEquals("01", result);
		result = type.parse("01йфяяя");
		assertEquals("01йфяяя", result);
	}
	
}
