package com.octo.code.practice.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.octo.code.practice.exception.CSVConverterCustomizedException;
import com.octo.code.practice.model.Column;
import com.octo.code.practice.utils.CSVConverterUtils;

public class MetadataFileReader {
	
	/**
	 * Load metadata information based on provided metadata file path
	 * @param metadataFilePath The metadata file path
	 * @return Returns a list of metadata
	 */
	public List<Column> loadMetadata(String metadataFilePath)
	{
		FileInputStream inputStream = null;
		List<Column> columns = new ArrayList<Column>();
		Scanner sc = null;
		String line = null;
		try {
			inputStream = new FileInputStream(metadataFilePath);

		    sc = new Scanner(inputStream, "UTF-8");
		    int startIndex = 0;
		    
		    while (sc.hasNextLine()) {
		        line = sc.nextLine();
		        if (line != null) {
		        	String[] metadataInfos = line.split(CSVConverterUtils.COMMA_DELIMITER);
		        	
		        	if (metadataInfos.length != 3) {
		        		String errorMessage = "Metada information: [" + line + "] is not valid, the length is supposed to be 3 after splitting with [" + CSVConverterUtils.COMMA_DELIMITER + "]";
		        		throw new CSVConverterCustomizedException(errorMessage);
		        	}
		        	
		        	int columnLength = Integer.parseInt(metadataInfos[1].trim());
		        	Column column = new Column(metadataInfos[0].trim(), startIndex, startIndex + columnLength , metadataInfos[2].trim());
		        	startIndex += Integer.parseInt(metadataInfos[1].trim());
		        	columns.add(column);
		        }
		    }

		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		    System.out.println("Load metadata information succesfully.");
		    return columns;
		} catch(NumberFormatException e) {
			e.printStackTrace();
			throw new CSVConverterCustomizedException("The length of the column string cannot be resolved as a number - metadata: [" + line + "]");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			throw new CSVConverterCustomizedException("Cannot find the file by provided path: " + metadataFilePath);
		} catch(IOException e) {
			e.printStackTrace();
			throw new CSVConverterCustomizedException("IO error in loading metadata information from the file.");
		} finally {
		    if (inputStream != null) {
		        try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new CSVConverterCustomizedException("Error while closing the inputStream.");
				}
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
	}
}
