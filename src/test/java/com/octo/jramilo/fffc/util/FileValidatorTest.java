package com.octo.jramilo.fffc.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileValidatorTest {
	private static final String TEST_FILENAME = "file.dat";
	
	@Rule
    public TemporaryFolder folder = new TemporaryFolder();
	
	@Test(expected = IllegalArgumentException.class)
	public void testInputFileNotExist() throws IOException {
		File file = new File(TEST_FILENAME);
		FileValidator.validate(file, true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOutputFileNotExist() throws IOException {
		File file = new File(TEST_FILENAME);
		FileValidator.validate(file, false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInputFileNoRead() throws IOException {
		File file = folder.newFile(TEST_FILENAME);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.close();
		file.setReadable(false);
		
		FileValidator.validate(file, true);
	}
	
	@Test
	public void testOutputFileNoRead() throws IOException {
		File file = folder.newFile(TEST_FILENAME);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.close();
		file.setReadable(false);
		
		FileValidator.validate(file, false);
	}
	
	@Test
	public void testInputFileNoWrite() throws IOException {
		File file = folder.newFile(TEST_FILENAME);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.close();
		file.setWritable(false);
		
		FileValidator.validate(file, true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOutputFileNoWrite() throws IOException {
		File file = folder.newFile(TEST_FILENAME);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.close();
		file.setWritable(false);
		
		FileValidator.validate(file, false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsDirectory() throws IOException {
		FileValidator.validate(folder.newFolder(), false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullFile() throws IOException {
		FileValidator.validate(null, false);
	}
	
}
