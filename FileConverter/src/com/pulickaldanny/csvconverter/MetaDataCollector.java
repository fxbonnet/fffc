package com.pulickaldanny.csvconverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*This class is for verifying and collecting information
 * from meta data file and store them in a list
 */
public class MetaDataCollector {
	/* method: collectMetaDataInfo
	 * Input: meta data file path
	 * Output: List of meta data
	 * Exception: CustomException
	 * Purpose: Verifies whether the meta data file is properly formatted. If formatted properly, meta data will be collected into list of MetaData 
	 * and will be returned to the caller.
	 */
	public List<MetaData> collectMetaDataInfo(final File file) throws CustomException {
		String errorMessage = "";
		int lineCount = 0;
		FileInputStream inputStream = null;
		Scanner sc = null;
		List<MetaData> metaDataList = new ArrayList<>();
		try {
			/*To read data from file*/
			inputStream = new FileInputStream(file);
			sc = new Scanner(inputStream, "UTF-8");
			/*Loop to read line by line from meta data file*/
			while (sc.hasNextLine()) {
				/*Counter for tracking line processing*/
				lineCount++;
				/*Save the line data into variable*/
				String line = sc.nextLine();
				/*Splitting line data using comma delimiter and save into String  array*/
				String[] information = line.split(",");
				/*Verifying whether meta data in the line has 3 information and throws exception if there are not enough information*/
				if (information.length != 3) {
					errorMessage += "Number of information provided in the line " + lineCount
							+ " of meta data file does not match requirement";
					System.out.println("Error detected. Error message: " + errorMessage);
					throw new CustomException(errorMessage + "Please correct the error and try again.");
				}
				/*System.out.println("Column Type: " + information[information.length - 1]);*/
				/*Verifying whether columnType is either String, Date or Numeric and throws exception if not either of these types*/
				if (!("String".equalsIgnoreCase(information[information.length - 1])
						|| "Date".equalsIgnoreCase(information[information.length - 1])
						|| "Numeric".equalsIgnoreCase(information[information.length - 1]))) {
					errorMessage += "Data Type " + information[information.length - 1] + " provided in the line "
							+ lineCount + " of meta data file is not understandable";
					System.out.println("Error detected. Error message: " + errorMessage);
					throw new CustomException(errorMessage + "Please correct the error and try again.");
				}
				/*Verifying whether columnLength is 10 if columnType is of date type*/
				if ("Date".equalsIgnoreCase(information[information.length - 1]) && !("10".equals(information[1]))) {
					errorMessage += "Length of " + information[information.length - 1] + " data type must be 10";
					System.out.println("Error detected. Error message: " + errorMessage);
					throw new CustomException(errorMessage + "Please correct the error and try again.");
				}
				/*Creating MetaData object and setting its properties*/
				MetaData md = new MetaData();
				md.setColumnName(information[0]);
				md.setColumnLength(Integer.parseInt(information[1]));
				md.setColumnType(information[2]);
				metaDataList.add(md);
			}
			
		/*Exception handling*/	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new CustomException("Meta data file not found");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(e.getMessage());
			/*Closing resources*/
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new CustomException("IO Exception while processing meta data file");
				}
			}
			if (sc != null) {
				sc.close();
			}
		}
		/*Returning metaData list to caller*/
		return metaDataList;
	}
}
