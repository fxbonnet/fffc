package com.fileConvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

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
		System.out.println(CustomMessage.TEXT_FILE_PATH);
		String textFilePath = scanner.nextLine();
		System.out.println(CustomMessage.TEXT_FILE_PATH_ENTERED + textFilePath);
		System.out.println(CustomMessage.CSV_FILE_PATH);
		String csvFilePath = scanner.nextLine();
		System.out.println(CustomMessage.CSV_FILE_PATH_ENTERED + csvFilePath);
		if (textFilePath.trim().length() > 0 && csvFilePath.trim().length() > 0) {
			try {
				new FileParser().readWriteFile(textFilePath, csvFilePath);
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
