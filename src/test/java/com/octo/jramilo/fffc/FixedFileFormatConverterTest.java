package com.octo.jramilo.fffc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.octo.jramilo.fffc.exception.InvalidFileExpection;
import com.octo.jramilo.fffc.exception.InvalidFormatException;

public class FixedFileFormatConverterTest {
	@Rule
    public TemporaryFolder folder = new TemporaryFolder();
	
	@Test
	public void testNominalCase() throws IOException, InvalidFormatException, InvalidFileExpection {
		File file = folder.newFile("metadata.txt");
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.write("Firstname,10,String\n");
        writer.write("Lastname,10,String\n");
        writer.write("Weight,5,numeric\n");
        writer.close();
        
        File dataInFile = folder.newFile("data.txt");
        PrintWriter dataWriter = new PrintWriter(dataInFile, "UTF-8");
        dataWriter.write("1970-01-01Jo,hn     Smith     81.5\n");
        dataWriter.write("1973-05-01J文*nn     Smith     90.5\n");
        dataWriter.write("1910-03-01mohn      Smith     87.5\n");
        dataWriter.close();
        
        File dataOutFile = folder.newFile("converted.csv");
        
        MetadataDescriptor descriptor = MetadataHelper.INSTANCE.describe(file);
        FixedFileFormatConverter converter = new FixedFileFormatConverter(descriptor);
        converter.convert(dataInFile, dataOutFile);
        
        FileReader fileReader = null;
		BufferedReader bufferedReader = null;
	
		try {
			fileReader = new FileReader(dataOutFile);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
		} finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
				
				if(fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
