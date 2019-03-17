package com.octo.code.practice.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.octo.code.practice.enums.ColumnTypeEnum;
import com.octo.code.practice.exception.CSVConverterCustomizedException;
import com.octo.code.practice.model.Column;
import com.octo.code.practice.utils.CSVConverterUtils;
import com.octo.code.practice.utils.DataParserUtils;

public class FixedFileCSVConverter {
	
	/**
	 * Generate the header line of the CSV file.
	 * @param generatedCSVFilePath The path of the generated CSV file path
	 * @param columns The metadata information
	 */
	public void generateCSVFiledHeader(String generatedCSVFilePath, List<Column> columns) {
		FileWriter fileWriter = null;
        
        try {
            fileWriter = new FileWriter(generatedCSVFilePath);
 
            //Write the CSV file header
            StringBuffer sbHeader = new StringBuffer();
            Column column = null;
            for(int i = 0; i < columns.size(); i++) {
            	column = columns.get(i);
            	sbHeader.append(column.getName());
            	if (i != columns.size() - 1) {
            		sbHeader.append(CSVConverterUtils.COMMA_DELIMITER);
            	}
            }
    
            //Add a new line separator after the header
            sbHeader.append(CSVConverterUtils.NEW_LINE_SEPARATOR);
            fileWriter.append(sbHeader.toString());
                  
            System.out.println("CSV file header is generated successfully.");
             
        } catch (Exception e) {
            System.out.println("Error in generating csv file headers.");
            e.printStackTrace();
        } finally {
             
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter.");
                e.printStackTrace();
            }
             
        }
	}

	/**
	 * Generate the body part of the CSV file
	 * @param dataFilePath Fix file path.
	 * @param generatedCSVFilePath Generated CSV file path.
	 * @param columns The metadata information.
	 */
	public void generateCSVFileBody(String dataFilePath, String generatedCSVFilePath, List<Column> columns) {
		FileInputStream inputStream = null;
		FileWriter fileWriter = null;
		Scanner sc = null;
		int rowIndex = 0;
		try {
			inputStream = new FileInputStream(dataFilePath);
			fileWriter = new FileWriter(generatedCSVFilePath, true);
			
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		    	rowIndex += 1;
		        String line = sc.nextLine();
		        StringBuffer sbLine = new StringBuffer();
		        Column column = null;
		        
		        for(int i = 0; i< columns.size(); i++) {
		        	column = columns.get(i);
		        	// If the length of row string is longer than maximum required length, but without causing any error
		        	// when covert the data. It will still be mark as valid here.
		        	String columnData = line.substring(column.getRowStartIndex(), column.getRowEndIndex());
		        	
		        	if (ColumnTypeEnum.DATE.equals(column.getType())) {
		        		sbLine.append(DataParserUtils.dateTypeFormat(columnData, rowIndex));
		        	} else if (ColumnTypeEnum.NUMBERIC.equals(column.getType())) {
		        		sbLine.append(DataParserUtils.numbericTypeFormat(columnData, rowIndex));
		        	} else {
		        		sbLine.append(DataParserUtils.stringTypeFormat(columnData, rowIndex));
		        	}
		        	
		        	if (i != columns.size() - 1) {
			        	sbLine.append(CSVConverterUtils.COMMA_DELIMITER);
		        	}
		        }
		        sbLine.append(CSVConverterUtils.NEW_LINE_SEPARATOR);
		        fileWriter.append(sbLine.toString());
		    }

		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		    
		    System.out.println("CSV file body part is generated successfully.");
		} catch(StringIndexOutOfBoundsException e) {
			 e.printStackTrace();
			 throw new CSVConverterCustomizedException("The data length of the row at: [" + rowIndex + "] is too short.");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			throw new CSVConverterCustomizedException("Cannot find the data file by provided path: " + dataFilePath);
		} catch(IOException e) {
			e.printStackTrace();
			throw new CSVConverterCustomizedException("IO error in loading data file by provided path: " + dataFilePath);
		} finally {
		    if (inputStream != null) {
		        try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new CSVConverterCustomizedException("Error while closing the inputStream.");
				}
		    }
		    
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                new CSVConverterCustomizedException("Error while flushing/closing fileWriter.");
            }
             
            
		    if (sc != null) {
		        sc.close();
		    }
		}
	}
}
