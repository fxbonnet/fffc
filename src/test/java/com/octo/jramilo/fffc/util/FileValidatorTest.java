package com.octo.jramilo.fffc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.octo.jramilo.fffc.exception.InvalidFileExpection;

public class FileValidatorTest {
	private static final String TEST_FILENAME = "file.dat";
	
	@Rule
    public TemporaryFolder folder = new TemporaryFolder();
	
	@After
	public void clean() {
	    File file = new File(TEST_FILENAME);
	    if(file.exists()) {
	    	file.delete();
	    }
	}
	
	@Test(expected = InvalidFileExpection.class)
	public void testInputFileNotExist() throws InvalidFileExpection {
		File file = new File(TEST_FILENAME);
		FileValidator.validate(file, true);
	}
	
	@Test(expected = InvalidFileExpection.class)
	public void testOutputFileNotExist() throws InvalidFileExpection {
		File file = new File(TEST_FILENAME);
		FileValidator.validate(file, false);
	}
	
	@Test(expected = InvalidFileExpection.class)
	public void testInputFileNoRead() throws InvalidFileExpection, FileNotFoundException, UnsupportedEncodingException {
		File file = new File(TEST_FILENAME);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.close();
		file.setReadable(false);
		
		FileValidator.validate(file, true);
	}
	
	@Test
	public void testOutputFileNoRead() throws InvalidFileExpection, FileNotFoundException, UnsupportedEncodingException {
		File file = new File(TEST_FILENAME);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.close();
		file.setReadable(false);
		
		FileValidator.validate(file, false);
	}
	
	@Test
	public void testInputFileNoWrite() throws InvalidFileExpection, FileNotFoundException, UnsupportedEncodingException {
		File file = new File(TEST_FILENAME);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.close();
		file.setWritable(false);
		
		FileValidator.validate(file, true);
	}
	
	@Test(expected = InvalidFileExpection.class)
	public void testOutputFileNoWrite() throws InvalidFileExpection, FileNotFoundException, UnsupportedEncodingException {
		File file = new File(TEST_FILENAME);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.close();
		file.setWritable(false);
		
		FileValidator.validate(file, false);
	}
	
	@Test(expected = InvalidFileExpection.class)
	public void testIsDirectory() throws InvalidFileExpection, IOException {
		FileValidator.validate(folder.newFolder(), false);
	}
	
	@Test(expected = InvalidFileExpection.class)
	public void testNullFile() throws InvalidFileExpection, IOException {
		FileValidator.validate(null, false);
	}
	
}
