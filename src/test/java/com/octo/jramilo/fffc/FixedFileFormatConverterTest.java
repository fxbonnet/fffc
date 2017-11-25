package com.octo.jramilo.fffc;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.util.Constant;
import com.octo.jramilo.fffc.util.ErrorMessage;
import com.octo.jramilo.fffc.util.MetadataParser;

public class FixedFileFormatConverterTest {
	private static final String CONVERTED_FILE_NAME = "converted.csv";
	private static final String DATA_IN_FILE_NAME = "datain.dat";
	private static final String METADATA_FILE_NAME = "metadata.txt";
	
	@Rule
    public TemporaryFolder folder = new TemporaryFolder();
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testNominalCase() throws IOException, InvalidFormatException {
		File file = createTestMetadataFile();
        File dataInFile = createNormalTestDataFile();
        File dataOutFile = folder.newFile(CONVERTED_FILE_NAME);
        
        MetadataDescriptor descriptor = MetadataParser.INSTANCE.parse(file);
        FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
        converter.convert(dataInFile, dataOutFile);
        
        ArrayList<String> expectedContents = new ArrayList<String>();
        expectedContents.add("Birthdate,Firstname,Lastname,Weight");
        expectedContents.add("1970/01/01,John,Smith,81.5");
        expectedContents.add("1973/05/01,Jenn,Smith,90.5");
        expectedContents.add("1910/03/01,mohn,Smith,87.5");
        
        LineIterator it = FileUtils.lineIterator(dataOutFile, Constant.CHARSET_UTF8);
		try {
			int idx = 0;
		    while (it.hasNext()) {
		        String line = it.nextLine();
		        String expected = expectedContents.get(idx++);
		        assertEquals(expected, line);
		    }
		} finally {
		    LineIterator.closeQuietly(it);
		}
	}
	
	@Test
	public void testWithCommaCase() throws IOException, InvalidFormatException {
		File file = createTestMetadataFile();
        
        File dataInFile = folder.newFile(DATA_IN_FILE_NAME);
        
		PrintWriter dataWriter = new PrintWriter(dataInFile, "UTF-8");
        dataWriter.write("1970-01-01John      Smith     81.5\n");
        dataWriter.write("1973-05-01Je,n      Smith     90.5\n");
        dataWriter.write("1910-03-01mohn      Smith     87.5\n");
        dataWriter.close();
        
        File dataOutFile = folder.newFile(CONVERTED_FILE_NAME);
        
        MetadataDescriptor descriptor = MetadataParser.INSTANCE.parse(file);
        FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
        converter.convert(dataInFile, dataOutFile);
        
        ArrayList<String> expectedContents = new ArrayList<String>();
        expectedContents.add("Birthdate,Firstname,Lastname,Weight");
        expectedContents.add("1970/01/01,John,Smith,81.5");
        expectedContents.add("1973/05/01,\"Je,n\",Smith,90.5");
        expectedContents.add("1910/03/01,mohn,Smith,87.5");
        
        LineIterator it = FileUtils.lineIterator(dataOutFile, Constant.CHARSET_UTF8);
		try {
			int idx = 0;
		    while (it.hasNext()) {
		        String line = it.nextLine();
		        String expected = expectedContents.get(idx++);
		        assertEquals(expected, line);
		    }
		} finally {
		    LineIterator.closeQuietly(it);
		}
	}
	
	@Test
	public void testWithSpecialCharCase() throws IOException, InvalidFormatException {
		File file = createTestMetadataFile();
        
        File dataInFile = folder.newFile(DATA_IN_FILE_NAME);
		PrintWriter dataWriter = new PrintWriter(dataInFile, "UTF-8");
        dataWriter.write("1970-01-01John      Smith     81.5\n");
        dataWriter.write("1973-05-01J文*n      Smith     90.5\n");
        dataWriter.write("1910-03-01mohn      Smith     87.5\n");
        dataWriter.close();
        
        File dataOutFile = folder.newFile(CONVERTED_FILE_NAME);
        
        MetadataDescriptor descriptor = MetadataParser.INSTANCE.parse(file);
        FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
        converter.convert(dataInFile, dataOutFile);
        
        ArrayList<String> expectedContents = new ArrayList<String>();
        expectedContents.add("Birthdate,Firstname,Lastname,Weight");
        expectedContents.add("1970/01/01,John,Smith,81.5");
        expectedContents.add("1973/05/01,J文*n,Smith,90.5");
        expectedContents.add("1910/03/01,mohn,Smith,87.5");
        
        LineIterator it = FileUtils.lineIterator(dataOutFile, Constant.CHARSET_UTF8);
		try {
			int idx = 0;
		    while (it.hasNext()) {
		        String line = it.nextLine();
		        String expected = expectedContents.get(idx++);
		        assertEquals(expected, line);
		    }
		} finally {
		    LineIterator.closeQuietly(it);
		}
	}
	
	@Test
	public void testNullInputFile() throws IOException, InvalidFormatException {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage(ErrorMessage.FILE_NULL);
		
		MetadataDescriptor descriptor = MetadataParser.INSTANCE.parse(createTestMetadataFile());
        FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
        converter.convert(null, folder.newFile("out.dat"));
	}
	
	@Test
	public void testEmptyInputFile() throws IOException, InvalidFormatException {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage(ErrorMessage.FILE_EMPTY);
		
		MetadataDescriptor descriptor = MetadataParser.INSTANCE.parse(createTestMetadataFile());
        FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
        converter.convert(folder.newFile("in.dat"), folder.newFile("out.dat"));
	}
	
	@Test
	public void testNullOutputFile() throws IOException, InvalidFormatException {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage(ErrorMessage.FILE_NULL);
		
		MetadataDescriptor descriptor = MetadataParser.INSTANCE.parse(createTestMetadataFile());
        FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
        converter.convert(createNormalTestDataFile(), null);
	}
	
	@Test
	public void testInvalidDateFormat() throws IOException, InvalidFormatException {
		expectedEx.expect(InvalidFormatException.class);
		expectedEx.expectMessage(ErrorMessage.DATE_CANNOT_BE_PARSED);
		
		File metadata = createTestMetadataFile();
		
		File invalidDateFormatData = folder.newFile("invalid_date.dat");
		PrintWriter dataWriter = new PrintWriter(invalidDateFormatData, "UTF-8");
        dataWriter.write("1970/01-01John      Smith     81.5\n");
		dataWriter.close();
		
		MetadataDescriptor descriptor = MetadataParser.INSTANCE.parse(metadata);
	    FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
	    File dataOutFile = folder.newFile(CONVERTED_FILE_NAME);
	    converter.convert(invalidDateFormatData, dataOutFile);
	}
	
	@Test
	public void testWeightNotNumericFormat() throws IOException, InvalidFormatException {
		expectedEx.expect(InvalidFormatException.class);
		expectedEx.expectMessage(ErrorMessage.WEIGHT_NOT_NUMERIC);
		
		File metadata = createTestMetadataFile();
		
		File invalidDateFormatData = folder.newFile("invalid_date.dat");
		PrintWriter dataWriter = new PrintWriter(invalidDateFormatData, "UTF-8");
        dataWriter.write("1970-01-01John      Smith     nnnn\n");
		dataWriter.close();
		
		MetadataDescriptor descriptor = MetadataParser.INSTANCE.parse(metadata);
	    FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
	    File dataOutFile = folder.newFile(CONVERTED_FILE_NAME);
	    converter.convert(invalidDateFormatData, dataOutFile);
	}
	
	@Test
	public void testOnlyWhiteSpaceDataFile() throws IOException, InvalidFormatException {
		expectedEx.expect(InvalidFormatException.class);
		expectedEx.expectMessage(ErrorMessage.BLANK_STRING);
		
		File metadata = createTestMetadataFile();
		
		File invalidDateFormatData = folder.newFile("invalid_date.dat");
		PrintWriter dataWriter = new PrintWriter(invalidDateFormatData, "UTF-8");
        dataWriter.write("          ");
		dataWriter.close();
		
		MetadataDescriptor descriptor = MetadataParser.INSTANCE.parse(metadata);
		FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
		File dataOutFile = folder.newFile(CONVERTED_FILE_NAME);
		converter.convert(invalidDateFormatData, dataOutFile);
	}
	
	private File createTestMetadataFile() throws IOException {
		File file = folder.newFile(METADATA_FILE_NAME);
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.write("Firstname,10,String\n");
        writer.write("Lastname,10,String\n");
        writer.write("Weight,5,numeric\n");
        writer.close();
        
        return file;
	}
	
	private File createNormalTestDataFile() throws IOException {
		File dataInFile = folder.newFile(DATA_IN_FILE_NAME);
        
		PrintWriter dataWriter = new PrintWriter(dataInFile, "UTF-8");
        dataWriter.write("1970-01-01John      Smith     81.5\n");
        dataWriter.write("1973-05-01Jenn      Smith     90.5\n");
        dataWriter.write("1910-03-01mohn      Smith     87.5\n");
        dataWriter.close();
        
        return dataInFile;
	}
}
