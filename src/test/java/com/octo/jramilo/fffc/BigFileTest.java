package com.octo.jramilo.fffc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.util.MetadataParser;

public class BigFileTest {
	private static final String CONVERTED_FILE_NAME = "converted.csv";
	private static final String METADATA_FILE_NAME = "metadata.txt";
	
	@Rule
    public TemporaryFolder folder = new TemporaryFolder();
	
	@Test
	public void testConvertBigFile() throws IOException, InvalidFormatException {
		File bigFile = create2GBFile();
		System.out.println("Data in size >>>>>>>>>" + bigFile.length());
		
		long timeBefore = System.currentTimeMillis();
		
		System.out.println("Converting...");
		
		File file = folder.newFile(METADATA_FILE_NAME);
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.write("Firstname,10,String\n");
        writer.write("Lastname,10,String\n");
        writer.write("Weight,5,numeric\n");
        writer.close();
        
		MetadataDescriptor descriptor = MetadataParser.INSTANCE.parse(file);
	    FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
	    File dataOutFile = folder.newFile(CONVERTED_FILE_NAME);
	    converter.convert(bigFile, dataOutFile);
	    
	    long timeAfter = System.currentTimeMillis();
	    
	    System.out.println("Time taken to convert ~2GB file " + (timeAfter - timeBefore) + " milliseconds");
	    System.out.println("Data out size >>>>>>>>>" + dataOutFile.length());
	}
	
	private File create2GBFile() throws IOException {
		File dataInFile = folder.newFile("huge.txt");
        
		PrintWriter dataWriter = new PrintWriter(dataInFile, "UTF-8");
		String dataTowrite = "1970-01-01John      Smith     81.5\n";
		long MAX = 2147483648L;
		long written = 0;
		
		while(written < MAX) {
			dataWriter.write(dataTowrite);
			written += dataTowrite.length();
		}
		
        dataWriter.close();
        
        return dataInFile;
	}
}
