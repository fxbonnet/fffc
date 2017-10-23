package com.fffc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.fffc.columns.Column;
import com.fffc.columns.DataFormatException;

/**
 * Converts a fixed file format defined by metadata and data file to a CSV file.
 * 
 * Our fixed file format files can have any number of columns
 * A column can be of 3 formats:
 * - date (format yyyy-mm-dd)
 * - numeric (decimal separator '.' ; no thousands separator ; can be negative)
 * - string
 * 
 * The structure of the file is described in a metadata file in csv format with a line for each column defining:
 * - column name
 * - column length
 * - column type
 * 
 * Example:
 * 
 * Data file:
 * 1970-01-01John           Smith           81.5
 * 1975-01-31Jane           Doe             61.1
 * 1988-11-28Bob            Big            102.4
 * 
 * Metadata file:
 * Birth date,10,date
 * First name,15,string
 * Last name,15,string
 * Weight,5,numeric
 * 
 * Output file:
 * Birth date,First name,Last name,Weight
 * 01/01/1970,John,Smith,81.5
 * 31/01/1975,Jane,Doe,61.1
 * 28/11/1988,Bob,Big,102.4
 * 
 * <b>
 * Note: Files are generated line by line and failure to do so
 * on a later step will not revert and clean already created files.
 * </b>
 * 
 */
public class FixedFileFormatConverter {
	
	private String outputCSVFilePath;
	private String dataFilePath;
	private String metaFilePath;

	/**
	 * Constructor
	 * @param metaFilePath
	 * 		full path to the metadata file
	 * @param dataFilePath
	 * 		full path to the data file
	 * @param outputCSVFilePath
	 * 		full path to the output CSV file
	 */
	public FixedFileFormatConverter(String metaFilePath, 
			String dataFilePath, String outputCSVFilePath) {
		this.metaFilePath = metaFilePath;
		this.dataFilePath = dataFilePath;
		this.outputCSVFilePath = outputCSVFilePath;
	}
	
	/**
	 * Converts the fixed file format to a CSV.
	 */
	public void convert() {
		if(metaFilePath == null) {
			throwIllegalArgument("metadata");
		}
		if(dataFilePath == null) {
			throwIllegalArgument("data");
		}
		if(outputCSVFilePath == null) {
			throwIllegalArgument("output csv");
		}
		
		List<Column> columns = new ArrayList<>();
		//read the metadata file extracting the columns
		try (BufferedReader metaFileReader = new BufferedReader(new FileReader(metaFilePath))) {
    	    String line;
    	    while ((line = metaFileReader.readLine()) != null) {
    	    	columns.add(Column.create(line.trim()));
    	    }
    	} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		//if no columns loaded -> no headers -> do not convert
		if(columns.isEmpty()) {
			return;
		}
		
		//read the data file using the extracted columns
		try (BufferedReader dataFileReader = new BufferedReader(new FileReader(dataFilePath));
				CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(outputCSVFilePath), CSVFormat.DEFAULT)) {
			
			//append headers in the final CSV
			for(Column column : columns) {
				csvPrinter.print(column.getName());
			}
			//end of record
			csvPrinter.println();

    	    String line;
    	    while ((line = dataFileReader.readLine()) != null) {
    	    	int index = 0;
    	    	for(Column column : columns) {
    	    		String value = null;
    	    		try {
    	    			value = line.substring(index, index + column.length()).trim();
    	    		} catch (IndexOutOfBoundsException e) {
    	    			throw new DataFormatException("Illegal column length.", e);
    	    		}
    	    		index += column.length();
    	    		csvPrinter.print(column.convert(value));
    	    	}
    	    	//end of record
    	    	csvPrinter.println();
    	    }
    	    
    	    csvPrinter.flush();
    	    
    	} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void throwIllegalArgument(String placeholder) {
		throw new IllegalArgumentException(String.format("Missing %s file.", placeholder));
	}
	
}
