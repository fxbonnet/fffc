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
				readWriteFile(textFilePath, csvFilePath);
				System.out.println(CustomMessage.CONVERT_FILE_COMPLETED);

			} catch (CustomException e) {
				System.out.println(e.getLocalizedMessage());
			} finally {
				scanner.close();

			}
		} else
			System.out.println(CustomMessage.INVALID_PATH);

	}

	/**
	 * This method validates the text data line by line and format the text that
	 * need to be written in CSV.
	 * 
	 * @param txtRead A variable of type String.
	 * @return String.
	 * @exception CustomException On error.
	 */

	private static String validate(String txtRead) throws CustomException {
		String txtDate = null;
		StringBuilder txtParser = new StringBuilder();
		if (txtRead != null && txtRead.length() > 0) {
			try {
				txtDate = CustomMessage.CSV_DATE_FORMAT
						.format((Date) CustomMessage.TXT_DATE_FORMAT.parse(txtRead.trim().substring(0, 10).toString()));
				txtParser.append(txtDate + CustomMessage.DELIMITER);
			} catch (ParseException e) {

				throw new CustomException(CustomMessage.DATE_PARSE_EXCEPTION);
			}
			String txtReadTemp = txtRead.trim().substring(10, txtRead.trim().length());
			String[] txtValues = txtReadTemp.replaceAll("\\s+", " ").split(" ");
			if (txtValues.length == 3) {
				for (int i = 0; i < txtValues.length; i++) {

					if (txtValues[i].length() < 16 && i < 2)

						txtParser.append(txtValues[i] + CustomMessage.DELIMITER);

					else if (i == 2 && txtValues[i].length() < 6 && !txtValues[i].matches(","))
						txtParser.append(txtValues[i]);
					else {
						if (i == 0)
							throw new CustomException(CustomMessage.INVALID_FIRST_NAME);
						else if (i == 1)
							throw new CustomException(CustomMessage.INVALID_LAST_NAME);
						else
							throw new CustomException(CustomMessage.INVALID_WEIGHT);
					}

				}
			} else
				throw new CustomException(CustomMessage.INVALID_DATA);

		}
		return txtParser.append(CustomMessage.NEW_LINE).toString();

	}

	/**
	 * This method creates a FileInputStream by opening a connection to an actual
	 * file, the file named by the File object file in the file system.
	 * 
	 * @param fileInputStream A variable of type FileInputStream.
	 * @param textFilePath    A variable of type String.
	 * @return FileInputStream.
	 * @exception CustomException On error.
	 */

	private static FileInputStream getTextFile(FileInputStream fileInputStream, String textFilePath)
			throws CustomException {
		try {
			fileInputStream = new FileInputStream(textFilePath);

		} catch (FileNotFoundException e) {

			throw new CustomException(CustomMessage.TEXT_FILE_NOT_FOUND);
		}

		return fileInputStream;
	}

	/**
	 * This method Creates a file output stream to write to the file represented by
	 * the specified File object.
	 * 
	 * @param fileOutputStream A variable of type FileOutputStream.
	 * @param csvFilePath      A variable of type String.
	 * @return FileOutputStream.
	 * @exception CustomException On error.
	 */

	private static FileOutputStream getCSVFile(FileOutputStream fileOutputStream, String csvFilePath)
			throws CustomException {
		try {
			fileOutputStream = new FileOutputStream(csvFilePath);
		} catch (FileNotFoundException e) {

			throw new CustomException(CustomMessage.CSV_FILE_NOT_FOUND);
		}
		return fileOutputStream;
	}

	/**
	 * This method read,validate and format the input text file and write it to the
	 * CSV file.
	 * 
	 * @param textFilePath A variable of type String.
	 * @param csvFilePath  A variable of type String.
	 * @return void.
	 * @exception CustomException On error.
	 */

	private static void readWriteFile(String textFilePath, String csvFilePath) throws CustomException {

		BufferedReader txtBufferReader = null;

		BufferedWriter csvBufferWriter = null;
		FileOutputStream fileOutputStream = null;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = getTextFile(fileInputStream, textFilePath);
			fileOutputStream = getCSVFile(fileOutputStream, csvFilePath);

			String nextLine = null;

			txtBufferReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF8"));
			csvBufferWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));

			int i = 0;
			while ((nextLine = txtBufferReader.readLine()) != null) {

				nextLine = validate(nextLine);
				if (i == 0) {
					// writes the header information in CSV file.
					csvBufferWriter.write(CustomMessage.HEADER_FORMAT);
					i = -1;

				}
				csvBufferWriter.write(nextLine);

			}

		} catch (IOException e) {
			throw new CustomException(CustomMessage.IOEXCEPTION);
		} catch (CustomException e) {
			throw new CustomException(e.getLocalizedMessage());
		} finally {

			try {
				// file.close();
				// csvFile.close();
				txtBufferReader.close();
				csvBufferWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// throw new CustomException(CustomMessage.RESOURCE_EXCEPTION);
			}

		}

	}

}
