package com.trips.ankur.fffc;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.trips.ankur.fffc.columns.Column;
import com.trips.ankur.fffc.data.DataParserAndWriter;
import com.trips.ankur.fffc.exceptions.InvalidFieldFormatException;
import com.trips.ankur.fffc.exceptions.InvalidLineFormatException;
import com.trips.ankur.fffc.metadata.MetaData;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * 
 * Test class
 * 
 * @author tripaank
 *
 */

public class DataParserAndWriterTest {

	private DataParserAndWriter parser;
	private final File metadata = new File(
			Thread.currentThread().getContextClassLoader().getResource("metadata").toURI());
	private final File dataFile = new File(
			Thread.currentThread().getContextClassLoader().getResource("datafile").toURI());
	private final File dataFile_withComma = new File(
			Thread.currentThread().getContextClassLoader().getResource("data_withComma").toURI());
	private final File dataFile_withIncorrectLength = new File(
			Thread.currentThread().getContextClassLoader().getResource("data_withIncorrectLength").toURI());
	private final File dataFile_withIncorrectFormat = new File(
			Thread.currentThread().getContextClassLoader().getResource("data_withIncorrectFormat").toURI());

	
	private static final String OUTPUTFILE_INCORRECTLINE= "outputfile_incorrect_line.csv";
	private static final String OUTPUTFILE_INCORRECTLINE_ERROR= "outputfile_incorrect_line.csv.error";
	private static final String OUTPUTFILE= "outputfile.csv";
	private static final String OUTPUTFILE_COMMA= "outputfile_comma.csv";
	private static final String OUTPUTFILE_INCORRECT= "outputfile_incorrect.csv";
	private static final String OUTPUTFILE_INCORRECT_ERROR= "outputfile_incorrect.csv.error";
	/*
	 * //Expected Output private final File expected_Comma = new
	 * File(Thread.currentThread().getContextClassLoader().getResource(
	 * "Expected_outputfile_comma.csv").toURI()); private final File
	 * expected_withIncorrectLength = new
	 * File(Thread.currentThread().getContextClassLoader().getResource(
	 * "Expected_outputfile_incorrect_line.csv").toURI()); private final File
	 * expected_withIncorrectFormat = new
	 * File(Thread.currentThread().getContextClassLoader().getResource(
	 * "Expected_outputfile_incorrect.csv").toURI());
	 */

	public DataParserAndWriterTest() throws URISyntaxException {
	}

	@Before
	public void setUp() throws Exception {

	}

	@AfterClass
	public static void deleteOutputFile() throws IOException {
		
		if(Files.exists(Paths.get(OUTPUTFILE_INCORRECTLINE)))
			Files.delete(Paths.get(OUTPUTFILE_INCORRECTLINE));	
		
		if(Files.exists(Paths.get(OUTPUTFILE_INCORRECTLINE_ERROR)))
			Files.delete(Paths.get(OUTPUTFILE_INCORRECTLINE_ERROR));	
		
		if(Files.exists(Paths.get(OUTPUTFILE)))
			Files.delete(Paths.get(OUTPUTFILE));
		
		if(Files.exists(Paths.get(OUTPUTFILE_COMMA)))
			Files.delete(Paths.get(OUTPUTFILE_COMMA));			
		
		if(Files.exists(Paths.get(OUTPUTFILE_INCORRECT)))
			Files.delete(Paths.get(OUTPUTFILE_INCORRECT));
		
		if(Files.exists(Paths.get(OUTPUTFILE_INCORRECT_ERROR)))
			Files.delete(Paths.get(OUTPUTFILE_INCORRECT_ERROR));			
		
	}
	
	

	@Test
	public void testMetadataFile() throws Exception {
		// Arrange
		parser = new DataParserAndWriter(metadata, dataFile);
		assertThat(parser, notNullValue());

		// Act
		MetaData md = parser.parseMetaData();
		Column[] columns = md.getColumns();

		// Assertions
		assertThat(md, notNullValue());
		assertThat(columns, notNullValue());
		assertThat(columns.length, is(4));

	}

	@Test
	public void testParseAndWriteCsvFile() throws Exception {
		// Arrange
		parser = new DataParserAndWriter(metadata, dataFile);
		assertThat(parser, notNullValue());

		// Act
		MetaData md = parser.parseMetaData();
		parser.parseDataAndWriteOutput(md, OUTPUTFILE, false);

		// Assertions
		assertThat(Files.exists(Paths.get(OUTPUTFILE)), is(true));
	}

	@Test(expected = InvalidFieldFormatException.class)
	public void testParseAndWriteCsvFile_IncorrectFieldFormat() throws Exception {
		// Arrange
		parser = new DataParserAndWriter(metadata, dataFile_withIncorrectFormat);
		assertThat(parser, notNullValue());

		// Act
		MetaData md = parser.parseMetaData();
		parser.parseDataAndWriteOutput(md, OUTPUTFILE_INCORRECT, false);

	}

	@Test()
	public void testParseAndWriteCsvFile_IncorrectFieldFormatWriteError() throws Exception {
		// Arrange
		parser = new DataParserAndWriter(metadata, dataFile_withIncorrectFormat);
		assertThat(parser, notNullValue());

		MetaData md = parser.parseMetaData();
		parser.parseDataAndWriteOutput(md, OUTPUTFILE_INCORRECT, true);

		// Validate if the output file is generated
		assertThat(Files.exists(Paths.get(OUTPUTFILE_INCORRECT)), is(true));

		// Validate if the error file is generated
		assertThat(Files.exists(Paths.get(OUTPUTFILE_INCORRECT_ERROR)), is(true));

	}

	@Test(expected = InvalidLineFormatException.class)
	public void testParseAndWriteCsvFile_IncorrectLineFormat() throws Exception {
		// Arrange
		parser = new DataParserAndWriter(metadata, dataFile_withIncorrectLength);
		assertThat(parser, notNullValue());

		// Act
		MetaData md = parser.parseMetaData();
		parser.parseDataAndWriteOutput(md, OUTPUTFILE_INCORRECT, false);

	}

	@Test()
	public void testParseAndWriteCsvFile_IncorrectLineFormatWriteError() throws Exception {
		// Arrange
		parser = new DataParserAndWriter(metadata, dataFile_withIncorrectLength);
		assertThat(parser, notNullValue());

		// Act
		MetaData md = parser.parseMetaData();
		parser.parseDataAndWriteOutput(md, OUTPUTFILE_INCORRECTLINE, true);
		// Validate if the output file is generated
		assertThat(Files.exists(Paths.get(OUTPUTFILE_INCORRECTLINE)), is(true));

		// Validate if the error file is generated
		assertThat(Files.exists(Paths.get(OUTPUTFILE_INCORRECTLINE_ERROR)), is(true));

	}

	@Test()
	public void testParseAndWriteCsvFile_ValidatesComma() throws Exception {
		// Arrange
		parser = new DataParserAndWriter(metadata, dataFile_withComma);
		assertThat(parser, notNullValue());

		// Act
		MetaData md = parser.parseMetaData();
		parser.parseDataAndWriteOutput(md, OUTPUTFILE_COMMA, true);
		// Validate if the output file is generated
		assertThat(Files.exists(Paths.get(OUTPUTFILE_COMMA)), is(true));
	}

}