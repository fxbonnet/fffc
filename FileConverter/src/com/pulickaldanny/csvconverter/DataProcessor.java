package com.pulickaldanny.csvconverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import javax.swing.JProgressBar;

/*The class is to verify and process the input data file into CSV file*/
public class DataProcessor {
	
	/* Method: processData
	 * Input: List of MetaData, input file, output file, JProgressBar object
	 * Output: NA
	 * Exception: CustomException*/
	public void processData(final List<MetaData> mdl, final File inputFile, final File outputFile,
			final JProgressBar jp) throws CustomException {
		/*Variables declaration*/
		long lineCount = 0;
		File ifile = inputFile;
		File ofile = outputFile;
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		Scanner sc = null;
		long countOfLines = 0;
		/*Getting number of lines in input data file*/
		try {
			System.out.println(LocalDateTime.now());
			countOfLines = Files.lines(Paths.get(inputFile.getPath())).count();
		} catch (IOException e) {
			System.out.println("No Input File Found");
			throw new CustomException("Missing Input Data File");
		}
		
		try {
			/*Preparing resources to open and read input data file*/
			inputStream = new FileInputStream(ifile);
			sc = new Scanner(inputStream, "UTF-8");
			outputStream = new FileOutputStream(ofile);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
			/*Preparing header line for writing into output file. Also calculating the expected length of a row in input file*/
			String headerLine = "";
			int maxLineLength = 0;
			for (MetaData metaData : mdl) {
				if (!"".equals(headerLine))
					headerLine += ",";
				headerLine += metaData.getColumnName();
				maxLineLength += metaData.getColumnLength();
			}
			System.out.println("Header Line:" + headerLine);
			/*Writing header line into output file*/
			bw.write(headerLine);
			bw.newLine();
			/*Reading line by line from input file*/
			while (sc.hasNextLine()) {
				lineCount++;
				/*Updating the progress bar*/
				jp.setValue((int) (lineCount / countOfLines) * 100);
				/*Storing data from memory into variable*/
				String line = sc.nextLine();
				/*Checking whether input line length is equal to length specified in meta data file. If not equal throws exception*/
				int linelength = line.length();
				if (linelength != maxLineLength) {
					bw.close();
					System.out.println("Error: Number of characters in line " + lineCount
							+ " in input data file does not meet meta data requirement");
					throw new CustomException("Error: Number of characters in line " + lineCount
							+ " in input data file does not meet meta data requirement. Please correct and try again");
				}
				/*Verification & processing of data*/
				int beginIndex = 0;
				int endIndex = 0;
				String stringtoWrite = "";
				for (MetaData metaData : mdl) {
					String formattedData = "";
					/*Chopping input data line according to length specified in meta data file*/
					endIndex = beginIndex + metaData.getColumnLength();
					String inputData = line.substring(beginIndex, endIndex);
					System.out.println("Extracted Data:" + inputData);
					/*Calling private methods to format data according to the data type*/
					if ("Date".equalsIgnoreCase(metaData.getColumnType())) {
						formattedData = getFormattedDate(inputData, lineCount);
					} else if ("String".equalsIgnoreCase(metaData.getColumnType())) {
						formattedData = getFormattedString(inputData, lineCount);
					} else if ("Numeric".equalsIgnoreCase(metaData.getColumnType())) {
						formattedData = getFormattedNumeric(inputData, lineCount);
					}
					/*Saving the formatted data into string variable*/
					if (!"".equals(stringtoWrite))
						stringtoWrite += ",";
					stringtoWrite += formattedData;
					beginIndex = endIndex;
				}
				/*Writing the data in variable into the output file*/
				bw.write(stringtoWrite);
				System.out.println("Line " + lineCount + ":" + stringtoWrite);
				bw.newLine();
			}
			/*Closing resource opened for writing into output file*/
			bw.close();
			/*Exception handling*/
		} catch (FileNotFoundException e) {
			System.out.println("No Input File Found");
			throw new CustomException("Missing Input Data File");
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("IOException while processing input data file");
		} finally {
			/*Closing resources opened for reading input file*/
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					throw new CustomException("IOException while processing input data file");
				}
			}
			if (sc != null) {
				sc.close();
			}
		}
	}
	
	/*
	 * Method: getFormattedNumeric
	 * Input: inputData, lineCount
	 * Output: Formatted numeric string
	 * Purpose: Verifies whether the passed data is of number type also without thousand separator (,). If these conditions are met, 
	 * numeric data in string format will be returned otherwise throws an exception
	 * */
	private String getFormattedNumeric(final String inputData, final long count) throws CustomException {
		/*Checks whether thousand separator included in the data passed*/
		if(inputData.indexOf(',')>=0)
			throw new CustomException("Error: Thousand separator detected at line " + count
					+ " in input data file. Please correct and try again");
		/*Checking whether data passed is of numeric type*/
		try {
			double number = Double.parseDouble(inputData);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new CustomException("Error: Input data at line " + count
					+ " in input data file is not a number. Please correct and try again");
		}
		return inputData.trim();
	}

	/*
	 * Method: getFormattedString
	 * Input: inputData, lineCount
	 * Output: Formatted string
	 * Purpose: If the data passed in has a comma in it, a trimmed string enclosed in double quotes will be returned otherwise a trimmed string will be returned
	 * */
	private String getFormattedString(final String inputData, final long count) {
		if(inputData.indexOf(',')<0)
			return inputData.trim();
		else
			return "\"" + inputData.trim() + "\"";
	}
	/*
	 * Method: getFormattedDate
	 * Input: inputData, lineCount
	 * Output: Formatted date (dd/MM/yyyy) as string
	 * Purpose: Verifies whether the data passed in is in right format, also the numbers are legal for date. If these conditions are met, 
	 * date formatted as YYYY/MM/DD in string format will be returned otherwise throws an exception
	 * */
	private String getFormattedDate(final String inputData, final long count) throws CustomException {
		int year, month, day = 0;
		try {
			String input[] = inputData.split("-");
			year = Integer.parseInt(input[0]);
			month = Integer.parseInt(input[1]);
			day = Integer.parseInt(input[2]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new CustomException("Error: Input data at line " + count
					+ " in input data file is not valid for date. Please correct and try again");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Error: Input data at line " + count
					+ " in input data file is not formatted correctly for date conversion. Correct format is yyyy-mm-dd. Please correct and try again");
		}
		try {
			LocalDate date = LocalDate.of(year, month, day);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return (date.format(formatter));
		} catch (Exception e) {
			throw new CustomException("Error: Input data at line " + count
					+ " in input data file is not valid for date. Please correct and try again");
		}

	}

}
