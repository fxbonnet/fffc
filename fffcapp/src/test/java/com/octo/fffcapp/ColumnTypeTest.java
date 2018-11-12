package com.octo.fffcapp;

import com.octo.fffcapp.exception.FixedFileFormatConverterParseException;
import com.octo.fffcapp.parser.MetadataFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

public class ColumnTypeTest {
	private static MetadataFormat.ColumnType dateType;
	private static MetadataFormat.ColumnType stringType;
	private static MetadataFormat.ColumnType numericType;
	
	@BeforeClass
	public static void setColumnTpeEnums() {
		dateType = MetadataFormat.ColumnType.DATE;
		stringType = MetadataFormat.ColumnType.STRING;
		numericType = MetadataFormat.ColumnType.NUMERIC;
	}
	
	@Test
	public void dateParsing() {
		String output = null;
		try {
			output = dateType.convert("1970-01-01");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}assertEquals("01/01/1970", output);
	}
	
	@Test
	public void invalidDateParsing() {
		try {
			dateType.convert("19701-01-01");
		} catch (FixedFileFormatConverterParseException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void validStringParsing() {
		String output = null;
		try {
			output = stringType.convert("Some name");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("Some name", output);
	}
	
	@Test
	public void stringParsingWithRightgTrimming() {
		String output = null;
		try {
			output = stringType.convert("Some name    ");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("Some name", output);
	}
	
	@Test
	public void stringParsingWithLeadingSpacesIntact() {
		String output = null;
		try {
			output = stringType.convert("   Some name    ");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("   Some name", output);
	}
	
	@Test
	public void stringParsingWithWrapping() {
		String output = null;
		try {
			output = stringType.convert("   Some, name    ");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("\"   Some, name\"", output);
	}
	
	@Test
	public void stringParsingOfNull() {
		String output = null;
		try {
			output = stringType.convert(null);
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("", output);
	}
	
	@Test
	public void stringParsingOfEmptyString() {
		String output = null;
		try {
			output = stringType.convert("");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("", output);
	}
	
	@Test
	public void stringParsingOfWhiteSpace() {
		String output = null;
		try {
			output = stringType.convert(" ");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("", output);
	}
	
	@Test
	public void validNumericParsing() {
		String output = null;
		try {
			output = numericType.convert(" 123 ");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("123", output);
	}
	
	@Test
	public void numericParsingWtihNegative() {
		String output = null;
		try {
			output = numericType.convert(" -123 ");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("-123", output);
	}
	
	@Test
	public void numericParsingWtihFraction() {
		String output = null;
		try {
			output = numericType.convert(" -123.123 ");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("-123.123", output);
	}
	
	@Test
	public void numericParsingOfBigValue() {
		String output = null;
		try {
			output = numericType.convert(" -123456789123456789123456789.123456789123456789123456789 ");
		} catch (FixedFileFormatConverterParseException e) {
			fail();
		}
		
		assertEquals("-123456789123456789123456789.123456789123456789123456789", output);
	}
	
	@Test
	public void invalidNumericParsingWithPlus() {
		try {
			numericType.convert("+1");
		} catch (FixedFileFormatConverterParseException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void invalidNumericParsingWithMoreThanOneDecimal() {
		try {
			numericType.convert("1.2.3");
		} catch (FixedFileFormatConverterParseException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void invalidNumericParsingWithScientificNotation() {
		try {
			numericType.convert("1.2e3");
		} catch (FixedFileFormatConverterParseException e) {
			assertTrue(true);
		}
	}
}
