package com.octo.interview.parsers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.octo.interview.exceptions.FileFormatException;
import com.octo.interview.metadata.Metadata;
import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;

public class FixedWidthFile {
	
	private static final String LINE_SEPARATOR = "\n";
	private static final String SOURCE_DATE_FORMAT = "yyyy-MM-dd";
	private static final String TARGET_DATE_FORMAT = "dd-MM-yyyy";
	
	private static final String DATE_COLUMN = "date";
	
	 
	private FixedWidthParser fixedParser;
	private Metadata metadata;
	private String filePath;
	
	private FixedWidthFile(FixedWidthParser fixedParser, Metadata metadata, String filePath) {
		this.metadata = metadata;
		this.fixedParser = fixedParser;
		this.filePath = filePath;
	}
	
	
	public ArrayList<String> getColNames() {
		return metadata.getColNames();
	}

	public static FixedWidthFile fromMetadata(Metadata metadata, String filePath) throws UnsupportedEncodingException, FileNotFoundException, ParseException {
		
		FixedWidthFields lengths = new FixedWidthFields(metadata.getColWidth());

		// creates the default settings for a fixed width parser
		FixedWidthParserSettings fixedSettings = new FixedWidthParserSettings(lengths);
		fixedSettings.getFormat().setPadding('_');

		//sets the character used for padding unwritten spaces in the file

		fixedSettings.getFormat().setLineSeparator(LINE_SEPARATOR);

		// creates a fixed-width parser with the given settings
		FixedWidthParser fixedParser = new FixedWidthParser(fixedSettings);
		
		return new FixedWidthFile(fixedParser, metadata, filePath);
	}
	
	public void open() throws UnsupportedEncodingException, FileNotFoundException {
		fixedParser.beginParsing(getReader(filePath));
	}
	
	public void close() {
		fixedParser.stopParsing();
	}
	
	public List<String[]> readRowsOfSize(int sizeInMb) throws FileFormatException{
		
		int numberOfRowsToRead =  (int)Math.ceil( Double.valueOf(sizeInMb *1024)/metadata.getRowLength());
		int rowCounter = 0;
		String[] row = null;
		List<String[]> fixedRows = new ArrayList<>();
		while (rowCounter < numberOfRowsToRead && (row = fixedParser.parseNext()) != null) {
			if (Stream.of(row).mapToInt(String::length).sum() > metadata.getRowLength()) {
				throw new FileFormatException("Row length is greater than the metadata definition");
			}
			fixedRows.add(row);
			rowCounter++;
		}
		
		List<String[]> finalRows;
		try {
			finalRows = applyTransformations(fixedRows, metadata.getColTypes());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new FileFormatException("One or more fields have invlid field length causing date fields to be unparsable");
		}
		if(row == null) {
			close();
		}
		if(rowCounter == 0) { // no rows has been read because the file has reached the end.
			return null;
		}

		return finalRows;
	}
	
	private static List<String[]> applyTransformations(List<String[]> data, List<String> colTypes) throws ParseException {
		for(int i = 0; i < colTypes.size(); i++) {
			if(colTypes.get(i).equals(DATE_COLUMN)){
				data = applyDateTransformation(data, i);
			}
		}
		return data;
	}
	
	private static List<String[]> applyDateTransformation(List<String[]> data, int colIndex) throws ParseException {
		
		for(String[] row : data) {
			SimpleDateFormat sourceFormat = new SimpleDateFormat(SOURCE_DATE_FORMAT);
			SimpleDateFormat targetFormat = new SimpleDateFormat(TARGET_DATE_FORMAT);
			Date dateValue = sourceFormat.parse(row[colIndex]);
			row[colIndex] = targetFormat.format(dateValue);
		}
		return data;
	}
	
	/**
	 * Creates a reader for a resource in the relative path
	 *
	 * @param relativePath relative path of the resource to be read
	 *
	 * @return a reader of the resource
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	private static Reader getReader(String relativePath) throws UnsupportedEncodingException, FileNotFoundException {
		
		return new InputStreamReader(new FileInputStream(relativePath), "UTF-8");
		
	}

}
