package com.octotechnology.fffc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.octotechnology.fffc.exception.FixedFileFormatParserException;
import com.octotechnology.fffc.util.DataTypeParser;

public class DataTypeParserTest {

	DataTypeParser dataTypeParser = new DataTypeParser();

	@Test
	public void dateParserTest() {
		String formattedDate = null;
		try {
			formattedDate = DataTypeParser.parseDate("2018-10-23");
		} catch (FixedFileFormatParserException e) {
			e.printStackTrace();
		}
		assertEquals("23/10/2018", formattedDate);
	}

	@Test
	public void stringParserTest() {
		String formattedString = null;
		try {
			formattedString = DataTypeParser.parseString("");
		} catch (FixedFileFormatParserException e) {
			fail();
		}
		assertEquals(StringUtils.EMPTY, formattedString);

		try {
			formattedString = DataTypeParser.parseString("\n");
		} catch (FixedFileFormatParserException e) {
			assertTrue(true);
		}

		try {
			formattedString = DataTypeParser.parseString("a,d");
		} catch (FixedFileFormatParserException e) {
			fail();
		}
		assertEquals(formattedString, "\"a,d\"");

	}

	@Test
	public void numericParserTest() {

		String formattedNumeric = null;

		try {
			formattedNumeric = DataTypeParser.parseNumeric("12345");
		} catch (FixedFileFormatParserException e) {
			fail();
			e.printStackTrace();
		}
		assertEquals("12345", formattedNumeric);

		try {
			formattedNumeric = DataTypeParser.parseNumeric("1A234");
		} catch (FixedFileFormatParserException e) {
			assertTrue(true);
		}

		try {
			formattedNumeric = DataTypeParser.parseNumeric("1.2345");
		} catch (FixedFileFormatParserException e) {
			fail();
			e.printStackTrace();
		}
		assertEquals("1.2345", formattedNumeric);

		try {
			formattedNumeric = DataTypeParser.parseNumeric("-12345");
		} catch (FixedFileFormatParserException e) {
			fail();
			e.printStackTrace();
		}
		assertEquals("-12345", formattedNumeric);
	}

}
