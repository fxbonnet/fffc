package com.octo.code.practice.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.octo.code.practice.exception.CSVConverterCustomizedException;
import com.octo.code.practice.utils.DataParserUtils;

public class DataParserUtilsTest {
	@Test
	public void testStringTypeFormat_intputDataIsNull() {
		String inputData = null;
		assertEquals(null, DataParserUtils.stringTypeFormat(inputData, 0));
	}
	
	@Test
	public void testStringTypeFormat_inputDataIncludesWhiteSpaces() {
		String inputData = "  test    ";
		assertEquals("test", DataParserUtils.stringTypeFormat(inputData, 0));
	}
	
	@Test
	public void testStringTypeFormat_inputDataInlcudesComma() {
		String inputData = "  tes,t    ";
		assertEquals("\"tes,t\"", DataParserUtils.stringTypeFormat(inputData, 0));
	}
	
	@Test(expected = CSVConverterCustomizedException.class)
	public void testDateTypeFormat_InputDataisInValid() {
		String inputData = "dd-MM-yyyy";
		DataParserUtils.dateTypeFormat(inputData, 0);	
	}
	
	@Test
	public void testDateTypeFormat_InputDataIsValid() {
		String inputData = "1970-01-01";
		String expectedDate = "01/01/1970";
		String formattedDate = DataParserUtils.dateTypeFormat(inputData, 0);	
		assertEquals(expectedDate, formattedDate);
	}
	
	@Test(expected = CSVConverterCustomizedException.class)
	public void testNumbericTypeFormat_inputDataIsInvalid() {
		String inputData = "test";
		DataParserUtils.numbericTypeFormat(inputData, 0);
	}
	
	@Test
	public void testNumbericTypeFormat_inputDataIsVvalid() {
		String inputData = "108.45";
		String expectedNumber = "108.45";
		String formattedNumber = DataParserUtils.numbericTypeFormat(inputData, 0);
		assertEquals(expectedNumber, formattedNumber);
	}
}
