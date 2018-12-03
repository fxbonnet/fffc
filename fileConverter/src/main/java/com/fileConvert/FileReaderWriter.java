package com.fileConvert;

import java.util.Scanner;

import com.common.CustomMessage;
import com.exception.CustomException;

/*
 * Fixed File Format converter.
 * 
 */
public class FileReaderWriter {

	/*
	 * This is the main method to read input text file and write to CSV file.
	 * 
	 */

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		
		//Enter text file name and location
		System.out.println(CustomMessage.TEXT_FILE_PATH);
		String textFilePath = scanner.nextLine();
		System.out.println(CustomMessage.TEXT_FILE_PATH_ENTERED + textFilePath);
		
		//Enter output csv file name and location
		System.out.println(CustomMessage.CSV_FILE_PATH);
		String csvFilePath = scanner.nextLine();
		System.out.println(CustomMessage.CSV_FILE_PATH_ENTERED + csvFilePath);
		
		//Enter metadata csv file name and location.
		System.out.println(CustomMessage.METADATA_FILE_PATH);
		String metaDataFilePath = scanner.nextLine();
		System.out.println(CustomMessage.METADATA_FILE_PATH_ENTERED + metaDataFilePath);

		//validate the location is entered or not.
		if (textFilePath.trim().length() > 0 && csvFilePath.trim().length() > 0
				&& metaDataFilePath.trim().length() > 0) {
			try {
				new FileParser().readWriteFile(textFilePath, csvFilePath, metaDataFilePath);
				System.out.println(CustomMessage.CONVERT_FILE_COMPLETED);

			} catch (CustomException e) {
				System.out.println(e.getLocalizedMessage());
			} finally {
				scanner.close();

			}
		} else
			System.out.println(CustomMessage.INVALID_PATH);

	}

}
