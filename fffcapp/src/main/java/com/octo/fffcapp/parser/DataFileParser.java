package com.octo.fffcapp.parser;

import java.util.stream.Stream;

import com.octo.fffcapp.exception.FixedFileFormatConverterIOException;
import com.octo.fffcapp.exception.FixedFileFormatConverterParseException;
import com.octo.fffcapp.file.FileWriter;

public class DataFileParser implements Parser<Stream<String>> {
	private static int lineCount;
	private MetadataFormat metadataFormat;
	private FileWriter writer;
	
	public DataFileParser(FileWriter writer) {
		this.metadataFormat = MetadataFormat.getInstance();
		this.writer = writer;
	}
	
	public void accept(Stream<String> stream) throws FixedFileFormatConverterParseException {
		lineCount = 0;
		
		writer.write(getHeaderLine());
		
		stream.forEach(this::process);
	}
	
	public String getHeaderLine() {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<metadataFormat.getColumnCount(); i++) {
			if(i>0) sb.append(",");
			sb.append(metadataFormat.getColumn(i).getHeader());
		}
		
		return sb.toString();
	}
	
	public void process(String line) throws FixedFileFormatConverterParseException, FixedFileFormatConverterIOException{
		++lineCount;
		
		int beg = 0;
		int end;
		StringBuilder output = new StringBuilder();
		for(int i=0; i<metadataFormat.getColumnCount(); i++) {
			if(i > 0) output.append(",");
			
			MetadataFormat.Column column = metadataFormat.getColumn(i);
			
			try {
				end = beg + column.getLength();
				
				output.append(column.getType().convert(line.substring(beg, end)));
				beg = end;
			} catch (FixedFileFormatConverterParseException e) {
				throw new FixedFileFormatConverterParseException("Unable to parse lin " + lineCount + " of data file.", e);
			}
		}
		
//		System.out.println(output);
		writer.write(output.toString());
	}
}
