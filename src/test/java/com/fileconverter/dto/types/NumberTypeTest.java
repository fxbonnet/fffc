package com.fileconverter.dto.types;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fileconverter.util.BLException;

public class NumberTypeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIfParsedNumbers() throws BLException {
		NumberType type = new NumberType();
		String result = type.parse("-100");
		assertEquals("-100", result);
		result = type.parse("-1.00");
		assertEquals("-1.00", result);
		result = type.parse("-.01");
		assertEquals("-.01", result);
	}
	
	@Test(expected=BLException.class)
	public void testIfNotParsedIncorrectNumber() throws BLException {
		NumberType type = new NumberType();
		String result = type.parse("-10w");
		assertEquals("-100", result);
	}

}
