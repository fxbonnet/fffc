package com.octo.fffcapp.parser;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.octo.fffcapp.exception.FixedFileFormatConverterParseException;

public class MetadataParser implements Parser<Stream<String>> {
	private static int lineCount;
	private MetadataFormat metadataFormat;
	
	public MetadataParser() {
		this.metadataFormat = MetadataFormat.getInstance();
	}
	
	public void accept(Stream<String> stream) throws FixedFileFormatConverterParseException {
		lineCount = 0;
		
		metadataFormat.clear();
		stream.forEach(this::process);
	}
	
	public void process(String line) throws FixedFileFormatConverterParseException{
		++lineCount;
		
		String[] elements = line.split(",");
		
		if(elements.length != 3) {
			throw new FixedFileFormatConverterParseException("Unable to parse line " + lineCount + " [" + line + "] in metadata file. "
					+ "Number of elements separated by comma(,) in metadata should be 3. "
					+ elements.length + " found.");
		}
		
		String header = elements[0];
		int length;
		try {
			length = Integer.parseInt(elements[1]);
		} catch(NumberFormatException e) {
			throw new FixedFileFormatConverterParseException("Unable to parse the length of column at line " + lineCount
					+ ". [" + elements[1] + "] is not an int." , e);
		}
		MetadataFormat.ColumnType type;
		try {
			type = MetadataFormat.ColumnType.valueOf(elements[2].toUpperCase());
		} catch(IllegalArgumentException e) {
			String possibleValues = Stream.of(MetadataFormat.ColumnType.values())
					.map(Object::toString)
					.collect(Collectors.joining(","));
			
			throw new FixedFileFormatConverterParseException("Unable to parse column type at line " + lineCount
					+ ". [" + elements[2] + "] is not a valid column type. "
							+ "Possible values [" + possibleValues + "] ignorning case." , e);
		}
		
		metadataFormat.addColumn(metadataFormat.new Column(header, length, type));
	}
}
