package com.octo.interview.metadata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class Metadata {
	
	private static final int HEADER_COLUMN_INDEX = 0;
	private static final int WIDTH_COLUMN_INDEX = 1;
	private static final int TYPE_COLUMN_INDEX = 2;
	private static final String LINE_SEPARATOR = "\n";
	
	private int[] colWidth; 
	private ArrayList<String> colNames; 
	private ArrayList<String> colTypes;
	
	private Metadata(int[] colWidth, ArrayList<String> colNames, ArrayList<String> colTypes)
	{
		this.colNames = colNames;
		this.colWidth = colWidth;
		this.colTypes = colTypes;
	}
	
	public int[] getColWidth() {
		return colWidth;
	}
	public ArrayList<String> getColNames() {
		return colNames;
	}
	public ArrayList<String> getColTypes() {
		return colTypes;
	}
	public int getRowLength() {
		return IntStream.of(colWidth).sum();
	}
	static public Metadata fromCsvFile(String csvFilePath) throws UnsupportedEncodingException, FileNotFoundException {
		
		CsvParserSettings settings = new CsvParserSettings();
		settings.getFormat().setLineSeparator(LINE_SEPARATOR);

		// creates a CSV parser
		CsvParser parser = new CsvParser(settings);

		// parses all rows in one go.
		List<String[]> allRows;
		allRows = parser.parseAll(getReader(csvFilePath));
		allRows.forEach((k) -> System.out.println(Arrays.toString(k)) );
		
		ArrayList<Integer> colWidth = new ArrayList<>();
		ArrayList<String> colNames = new ArrayList<>();
		ArrayList<String> colTypes = new ArrayList<>();
		for(String[] row : allRows) {
			colWidth.add(Integer.parseInt(row[WIDTH_COLUMN_INDEX]));
			colNames.add(row[HEADER_COLUMN_INDEX]);
			colTypes.add(row[TYPE_COLUMN_INDEX]);
		}
		return new Metadata(colWidth.stream()
				.mapToInt(Integer::intValue)
				.toArray(), colNames, colTypes);
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
