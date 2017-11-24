package com.octo.jramilo.fffc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.octo.jramilo.fffc.exception.InvalidFileExpection;
import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.model.Metadata;
import com.octo.jramilo.fffc.processor.CsvConverter;
import com.octo.jramilo.fffc.util.FileValidator;

/**
 * A class that converts a given input file to a CSV format.
 * 
 * 
 * @author jacobramilo
 *
 */
public class FixedFileFormatConverter {
	private MetadataDescriptor mDescriptor;
	private static final String CRLF = "\r\n";
	
	public FixedFileFormatConverter(MetadataDescriptor mDescriptor) {
		this.mDescriptor = mDescriptor;
	}
	
	/**
	 * A method the does the conversion of the data file to a CSV format.<br/>
	 * <b>Example:</b><br/>
	 * Input:<br/>
	 * 1970-01-01John           Smith           81.5<br/>
	 * 1975-01-31Jane           Doe             61.1<br/>
	 * <br/>
	 * Output:<br/>
	 * 01/01/1970,John,Smith,81.5<br/>
	 * 31/01/1975,Jane,Doe,61.1
	 * 
	 * 
	 * @param input
	 * @param output
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws InvalidFileExpection
	 */
	public void convert(File input, File output) throws IOException, InvalidFormatException, InvalidFileExpection {
//		FileValidator.validate(input, true);
//		FileValidator.validate(output, false);
		
		
		
		
		
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		PrintWriter writer = null;
	
		try {
			fileReader = new FileReader(input);
			bufferedReader = new BufferedReader(fileReader);
			writer = new PrintWriter(output);
			
//			Files.lines(Paths.get(input)).forEach(line -> {
//				writer.print(CsvConverter.covert(mDescriptor, line) + CRLF);
//			});
//			
//			
//			try (Stream<String> stream = Files.lines(Paths.get(input))) {
//
//				stream.forEach(line -> {
//					writer.print(CsvConverter.covert(mDescriptor, line) + CRLF);
//				});
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			writer.print(getHeader() + CRLF);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				writer.print(CsvConverter.covert(mDescriptor, line) + CRLF);
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
	
	private String getHeader() {
		return String.join(",", mDescriptor.getMetadataList().stream()
			    .map(Metadata::getName)
			    .collect(Collectors.toList())
			);
	}
}
