package service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.FileConvertorException;
import model.CsvMetaData;

public class CsvMetaDataReaderTest {

	CsvMetaDataReader csvMetaDataReader = null;
	
	@Before
	public void setUp() throws Exception {
		csvMetaDataReader = new CsvMetaDataReader();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = FileConvertorException.class)
	public void testWhenMetadataCSVFileDeosNotExist() throws FileConvertorException, IOException {
		final String path = "c:\\temp\\metadata\\file\\doesnot\\exist.csv";
		
		csvMetaDataReader.readMetaDataFile(path);
	}

	@Test
	public void testWhenMetadataCSVFileExistAndValid() throws FileConvertorException, IOException {
		final String path = "c:\\temp\\metadata.csv";
		
		List<CsvMetaData> list = csvMetaDataReader.readMetaDataFile(path);
		System.out.println(list);
		
		assertEquals(list.size(), 4);
	}
	
	@Test(expected = FileConvertorException.class)
	public void testWhenMetadataCSVFileExistAndInvalid() throws FileConvertorException, IOException {
		final String path = "c:\\temp\\metadataInvalid.csv";
		
		csvMetaDataReader.readMetaDataFile(path);
		
	}
}
