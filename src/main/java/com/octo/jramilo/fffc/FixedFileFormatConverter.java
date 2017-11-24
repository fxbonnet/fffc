package com.octo.jramilo.fffc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.processor.CsvConverter;

public class FixedFileFormatConverter {
	private MetadataDescriptor mDescriptor;
	
	public FixedFileFormatConverter(MetadataDescriptor mDescriptor) {
		this.mDescriptor = mDescriptor;
	}
	
	public void convert(File input, File output) throws IOException, InvalidFormatException {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		PrintWriter writer = null;
	
		try {
			fileReader = new FileReader(input);
			bufferedReader = new BufferedReader(fileReader);
			writer = new PrintWriter(output);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				writer.print(CsvConverter.covert(mDescriptor, line) + "\r\n");
			}
			
		} finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
				
				if(fileReader != null) {
					fileReader.close();
				}
				
				if(writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
