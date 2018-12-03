package com.fileConvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import com.common.CustomMessage;
import com.exception.CustomException;
import com.model.MetaData;

public class FileParser implements IParser {

	/**
	 * This method read,validate and format the input text file as per the metadata
	 * file and write it to the CSV file.
	 * 
	 * @param textFilePath     A variable of type String.
	 * @param csvFilePath      A variable of type String.
	 * @param metaDataFilePath A variable of type String.
	 * @return void.
	 * @exception CustomException On error.
	 */

	public void readWriteFile(String textFilePath, String csvFilePath, String metaDataFilePath) throws CustomException {

		BufferedReader txtBufferReader = null;
		BufferedReader metaDataBufferReader = null;

		BufferedWriter csvBufferWriter = null;
		FileOutputStream fileOutputStream = null;
		FileInputStream fileInputStream = null;
		FileInputStream metaDatafileInputStream = null;

		try {
			// creates Metafile reader.
			metaDatafileInputStream = getMetaDataFile(metaDatafileInputStream, metaDataFilePath);
			metaDataBufferReader = new BufferedReader(new InputStreamReader(metaDatafileInputStream, "UTF8"));

			// Creates input text file reader.
			fileInputStream = getTextFile(fileInputStream, textFilePath);
			txtBufferReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF8"));

			// Creates output CSV writter.
			fileOutputStream = getCSVFile(fileOutputStream, csvFilePath);
			csvBufferWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));

			String nextLine = null;
			// Parse metafile data.
			List<MetaData> metadataValues = new MetaDataFileReader().parseMetaFile(metaDataBufferReader);
			
			int i = 0;
			// Reads the text input file line by line and writes to the output CSV file.
			while ((nextLine = txtBufferReader.readLine()) != null) {

				nextLine = new TextFileReader().parseTextFile(nextLine, metadataValues);
				if (i == 0) {
					// writes the header information in CSV file.
					String headerNames = new MetaDataFileReader().writeCSVHeader(metadataValues);
					csvBufferWriter.write(headerNames);
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
				throw new CustomException(CustomMessage.RESOURCE_EXCEPTION);
			}

		}

	}

	/**
	 * This method creates a FileInputStream by opening a connection to an metadata
	 * csv file.
	 * 
	 * @param metaDatafileInputStream A variable of type FileInputStream.
	 * @param metaDataFilePath        A variable of type String.
	 * @return FileInputStream.
	 * @exception CustomException On error.
	 */

	private FileInputStream getMetaDataFile(FileInputStream metaDatafileInputStream, String metaDataFilePath)
			throws CustomException {
		try {
			metaDatafileInputStream = new FileInputStream(metaDataFilePath);

		} catch (FileNotFoundException e) {

			throw new CustomException(CustomMessage.METADATA_FILE_NOT_FOUND);
		}

		return metaDatafileInputStream;
	}

	/**
	 * This method creates a FileInputStream by opening a connection to an text
	 * input file.
	 * 
	 * @param fileInputStream A variable of type FileInputStream.
	 * @param textFilePath    A variable of type String.
	 * @return FileInputStream.
	 * @exception CustomException On error.
	 */

	private FileInputStream getTextFile(FileInputStream fileInputStream, String textFilePath) throws CustomException {
		try {
			fileInputStream = new FileInputStream(textFilePath);

		} catch (FileNotFoundException e) {

			throw new CustomException(CustomMessage.TEXT_FILE_NOT_FOUND);
		}

		return fileInputStream;
	}

	/**
	 * This method Creates a file output stream to write to the output csv file.
	 * 
	 * @param fileOutputStream A variable of type FileOutputStream.
	 * @param csvFilePath      A variable of type String.
	 * @return FileOutputStream.
	 * @exception CustomException On error.
	 */

	private FileOutputStream getCSVFile(FileOutputStream fileOutputStream, String csvFilePath) throws CustomException {
		try {
			fileOutputStream = new FileOutputStream(csvFilePath);
		} catch (FileNotFoundException e) {

			throw new CustomException(CustomMessage.CSV_FILE_NOT_FOUND);
		}
		return fileOutputStream;
	}

}
