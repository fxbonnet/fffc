package model;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.FileConvertorException;
import model.CsvMetaData;

public class CsvMetaDataTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWhenMetadataPresent() throws FileConvertorException {
		final String colName = "Birth Date";
		final String colLength = "10";
		final String colDate = "Date";
		
		CsvMetaData csvMetaData = new CsvMetaData(colName, colLength, colDate);
		
		assertThat(csvMetaData.getColName(), is(colName));
		assertThat(csvMetaData.getColLength(), is(colLength));
		assertThat(csvMetaData.getColType(), is(colDate));
	}

	@Test(expected = FileConvertorException.class)
	public void testWhenMetadataColumnNameIsInvalid() throws FileConvertorException {
		final String colName = "";
		final String colLength = "10";
		final String colDate = "Date";
		
		CsvMetaData csvMetaData = new CsvMetaData(colName, colLength, colDate);
		
	}
	
	@Test(expected = FileConvertorException.class)
	public void testWhenMetadataColumnLengthIsInvalid() throws FileConvertorException {
		final String colName = "TestColumn";
		final String colLength = StringUtils.EMPTY;
		final String colDate = "Date";
		
		CsvMetaData csvMetaData = new CsvMetaData(colName, colLength, colDate);
		
	}

	@Test(expected = FileConvertorException.class)
	public void testWhenMetadataColumnTypeIsInvalid() throws FileConvertorException {
		final String colName = "TestColumn";
		final String colLength = "10";
		final String colDate = null;
		
		CsvMetaData csvMetaData = new CsvMetaData(colName, colLength, colDate);
		
	}
}
