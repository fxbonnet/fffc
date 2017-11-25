package com.octo.jramilo.fffc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.model.Metadata;
import com.octo.jramilo.fffc.util.Constant;
import com.octo.jramilo.fffc.util.CsvConverter;
import com.octo.jramilo.fffc.util.ErrorMessage;
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
	 * @param input - The input file containing the data
	 * @param output - The file to output the data.<br/>NOTE: It is the 
	 * responsibility of the caller to create the file.
	 * 
	 * @throws IOException - thrown if there is an input/output exception
	 * @throws InvalidFormatException - thrown if the file contents format is invalid.
	 */
	public void convert(File input, File output) throws IOException, InvalidFormatException {
		FileValidator.validate(input, true);
		FileValidator.validate(output, false);
		
		PrintWriter writer = null;
		LineIterator it = FileUtils.lineIterator(input, Constant.CHARSET_UTF8);
		try {
			writer = new PrintWriter(output);
			writer.print(getHeader() + CRLF);
		    while (it.hasNext()) {
		        String line = it.nextLine();
		        if(StringUtils.isAllBlank(line)) {
					throw new InvalidFormatException(ErrorMessage.BLANK_STRING);
				}
				
		        writer.print(CsvConverter.covert(mDescriptor, line) + CRLF);
		    }
		} finally {
		    LineIterator.closeQuietly(it);
		    if(writer != null) {
				writer.close();
			}
		}
	}
	
	private String getHeader() {
		return String.join(Constant.COMMA, mDescriptor.getMetadataList().stream()
			    .map(Metadata::getName)
			    .collect(Collectors.toList()));
	}
}
