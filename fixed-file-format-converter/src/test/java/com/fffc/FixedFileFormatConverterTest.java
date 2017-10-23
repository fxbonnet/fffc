package com.fffc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fffc.columns.DataFormatException;

/**
 * TODO Add more tests.
 *
 */
public class FixedFileFormatConverterTest 
{

	private final static String META_SAMPLE = "sample-meta";
	private final static String DATA_SAMPLE = "sample-data";
	private final static String CSV_SAMPLE = "sample-csv";
	private FixedFileFormatConverter converter;
	private Path tmpMetaFilePath;
	private Path tmpDataFilePath;
	private Path tmpCSVFilePath;
	
	private List<Path> createdFiles;
	
	@Before
	public void setUp() throws IOException {
		createdFiles = new ArrayList<>();
		tmpMetaFilePath = Paths.get(File.createTempFile(UUID.randomUUID().toString(), ".tmp").toURI());
		tmpDataFilePath = Paths.get(File.createTempFile(UUID.randomUUID().toString(), ".tmp").toURI());
		tmpCSVFilePath = Paths.get(File.createTempFile(UUID.randomUUID().toString(), ".tmp").toURI());
		createdFiles.add(tmpMetaFilePath);
		createdFiles.add(tmpDataFilePath);
		createdFiles.add(tmpCSVFilePath);
		converter = new FixedFileFormatConverter(
				tmpMetaFilePath.toString(), 
				tmpDataFilePath.toString(), 
				tmpCSVFilePath.toString());
	}
	
	@After
	public void tearDown() throws IOException {
		for(Path p : createdFiles) {
			Files.deleteIfExists(p);
		}
	}
	
	@Test
	public void testSamples() throws IOException {
		//prepare examples
		prepareData(tmpMetaFilePath, getClass().getResourceAsStream(File.separator + META_SAMPLE));
		prepareData(tmpDataFilePath, getClass().getResourceAsStream(File.separator + DATA_SAMPLE));
		prepareData(tmpCSVFilePath, getClass().getResourceAsStream(File.separator + CSV_SAMPLE));
		
		converter.convert();
		
		assertSampleEqualsOutput();
	}
	
	@Test
	public void testEmpty() throws IOException {
		prepareFromContent(Collections.emptyList(), Collections.emptyList());
		converter.convert();
		List<String> actual = Files.readAllLines(tmpCSVFilePath);
		Assert.assertEquals(0, actual.size());
	}
	
	@Test
	public void testMetaAndEmptyData() throws IOException {
		prepareFromContent(readResouceLines(META_SAMPLE), Collections.emptyList());
		converter.convert();
		List<String> actual = Files.readAllLines(tmpCSVFilePath);
		Assert.assertEquals(1, actual.size());
		List<String> headersOnly = readResouceLines(CSV_SAMPLE).subList(0, 1);
		Assert.assertEquals(headersOnly, actual);
	}
	
	@Test
	public void testEmptyMetaAndData() throws IOException {
		//non empty data
		List<String> data = readResouceLines(DATA_SAMPLE);
		Assert.assertTrue(data.size() > 0);
		
		prepareFromContent(Collections.emptyList(), data);
		List<String> actual = Files.readAllLines(tmpCSVFilePath);
		//empty csv file
		Assert.assertEquals(0, actual.size());
	}
	
	@Test
	public void testIllegalLength() {
		prepareFromContent(Arrays.asList("Birthday,29,date"), Arrays.asList("2017"));
		try {
			converter.convert();
			Assert.fail("Illegal column length not caught!");
		} catch (DataFormatException e) {
			//ok
			Assert.assertTrue(IndexOutOfBoundsException.class.isAssignableFrom(e.getCause().getClass()));
		}
	}
	
	@Test
	public void testIllegalDecimal() {
		prepareFromContent(Arrays.asList("number,4,numeric"), Arrays.asList("0.-1"));
		try {
			converter.convert();
			Assert.fail("Illegal number not caught");
		} catch (DataFormatException e) {
			//ok
			Assert.assertEquals(NumberFormatException.class, e.getCause().getClass());
		}
	}
	
	@Test
	public void testIllegalDate() {
		prepareFromContent(Arrays.asList("birthday,10,date"), Arrays.asList("2017/10/01"));
		try {
			converter.convert();
			Assert.fail("Illegal date format not caught");
		} catch (DataFormatException e) {
			//ok
			Assert.assertEquals(ParseException.class, e.getCause().getClass());
		}
	}
	
	@Test
	public void testStringWithComma() throws IOException {
		prepareFromContent(Arrays.asList("string,5,string"), Arrays.asList("ab,cd"));
		converter.convert();
		//string
		//"ab,cd"
		//get index 1
		String actual = Files.readAllLines(tmpCSVFilePath).get(1);
		String expected = "\"ab,cd\"";
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void testShortArgs() throws IOException {
		prepareData(tmpMetaFilePath, getClass().getResourceAsStream(File.separator + META_SAMPLE));
		prepareData(tmpDataFilePath, getClass().getResourceAsStream(File.separator + DATA_SAMPLE));
		
		String[] args = new String[]{
				"-m", tmpMetaFilePath.toString(), 
				"-d", tmpDataFilePath.toString(), 
				"-c", tmpCSVFilePath.toString()};
		try {
			Main.main(args);
		} catch (org.apache.commons.cli.ParseException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		assertSampleEqualsOutput();
	}
	
	@Test
	public void testLongArgs() throws IOException {
		prepareData(tmpMetaFilePath, getClass().getResourceAsStream(File.separator + META_SAMPLE));
		prepareData(tmpDataFilePath, getClass().getResourceAsStream(File.separator + DATA_SAMPLE));
		
		String[] args = new String[]{
				"--meta-file", tmpMetaFilePath.toString(), 
				"--data-file", tmpDataFilePath.toString(), 
				"--csv-file", tmpCSVFilePath.toString()};
		try {
			Main.main(args);
		} catch (org.apache.commons.cli.ParseException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		assertSampleEqualsOutput();
	}
	
	@Test
	public void testIllegalArgument() {
		String[] args = new String[]{
				"--data-file", tmpDataFilePath.toString(), 
				"--csv-file", tmpCSVFilePath.toString()};
		try {
			Main.main(args);
			Assert.fail();
		} catch (org.apache.commons.cli.ParseException e) {
			Assert.fail();
		} catch (IllegalArgumentException e) {
			//ok
		}
	}
	
	protected void assertSampleEqualsOutput() throws IOException {
		List<String> expected = readResouceLines(CSV_SAMPLE);
		List<String> actual = Files.readAllLines(tmpCSVFilePath);
		Assert.assertEquals(expected, actual);
	}
	
	protected void prepareFromContent(List<String> metaData, List<String> data) {
		prepareData(tmpMetaFilePath, metaData);
		prepareData(tmpDataFilePath, data);
	}
	
	protected void prepareData(Path path, InputStream resourceAsStream) {
        try {
			Files.deleteIfExists(path);
			Files.copy(resourceAsStream, path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
	
	protected void prepareData(Path path, List<String> actualData) {
		try {
			Files.deleteIfExists(path);
			Files.write(path, actualData, StandardOpenOption.CREATE);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected List<String> readResouceLines(String resourcePath) {
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(File.separator + resourcePath)))) {
			return buffer.lines().collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
