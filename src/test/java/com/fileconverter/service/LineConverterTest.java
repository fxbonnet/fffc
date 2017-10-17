package com.fileconverter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fileconverter.dto.SourceFileItemStructure;
import com.fileconverter.dto.types.DateType;
import com.fileconverter.dto.types.NumberType;
import com.fileconverter.dto.types.StringType;
import com.fileconverter.util.BLException;

public class LineConverterTest {

	private static LineConverter converter;
	private static List<SourceFileItemStructure> structures;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		converter = new LineConverter();
		structures = new ArrayList<>();
		structures.add(converter.parseInputStructure("Birth date,10,date"));
		structures.add(converter.parseInputStructure("First name,15,string"));
		structures.add(converter.parseInputStructure("Last name,15,string"));
		structures.add(converter.parseInputStructure("Weight,5,numeric"));
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testIfCorrectType() throws BLException {
		SourceFileItemStructure result = structures.get(0);
		assertTrue("expected object is dateType", (result.getType() instanceof DateType));
		assertEquals(10,result.getLength());
		assertEquals("Birth date", result.getName());
		result = structures.get(1);
		assertTrue("expected object is StringType", (result.getType() instanceof StringType));
		assertEquals(15, result.getLength());
		assertEquals("First name",result.getName());
		result = structures.get(3);
		assertTrue("expected object is NumberType", (result.getType() instanceof NumberType));
		assertEquals(5, result.getLength());
		assertEquals("Weight", result.getName());
	}
	
	@Test(expected=BLException.class)
	public void testIfNotParsedIncorrectType() throws BLException {
		converter.parseInputStructure("Birth date,10,dat");
	}

	@Test(expected=BLException.class)
	public void testIfNotParsedIncorrectNumber() throws BLException {
		converter.parseInputStructure("Birth date,p0,date");
	}
	
	@Test(expected=BLException.class)
	public void testIfNotParsedAsZeroLength() throws BLException {
		converter.parseInputStructure("Birth date,0,date");
	}
	
	@Test
	public void testIfCorrectlyParsingSourceString() throws BLException {
		String result = converter.parseSourceLine("1970-01-01John           Smith           81.5", structures);
		assertEquals("01/01/1970,John,Smith,81.5\n", result);
		result = converter.parseSourceLine("1975-01-31Jane           Doe             61.1", structures);
		assertEquals("31/01/1975,Jane,Doe,61.1\n", result);
		result = converter.parseSourceLine("1988-11-28Bob            Big            102.4", structures);
		assertEquals("28/11/1988,Bob,Big,102.4\n", result);
	}
	
	@Test
	public void testIfCorrectlyCreatingHeader() throws BLException {
		String result = converter.parseHeader(structures);
		assertEquals("Birth date,First name,Last name,Weight\n", result);
	}


}
