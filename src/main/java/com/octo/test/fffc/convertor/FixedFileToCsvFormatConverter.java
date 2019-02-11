package com.octo.test.fffc.convertor;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class FixedFileToCsvFormatConverter {
	private ArrayList<String[]> getFileMetaData(String metaDataFileName) throws IOException {
		ArrayList<String[]> metaDataList = new ArrayList<>();
		CSVReader reader = null;

		try {
			reader = new CSVReader(new FileReader(metaDataFileName));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				String dataType = nextLine[2];
				String dataLength = Integer.decode(nextLine[1]).toString();
				String[] metaDataRow = new String[] { nextLine[0], dataLength, dataType };
				metaDataList.add(metaDataRow);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return metaDataList;
	}

	private String changeDateFormat(String inputDateString) throws IOException {
		try {
			DateTimeFormatter oldPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate = LocalDate.parse(inputDateString, oldPattern);
			return localDate.format(newPattern);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	private void convertToCsvFile(String metaDataFileName, String fixedFormatFileName, String csvFileName)
			throws IOException {
		ArrayList<String[]> metaDataList = getFileMetaData(metaDataFileName);

		FileInputStream inputStream = null;
		Scanner sc = null;
		CSVWriter writer = null;

		try {
			inputStream = new FileInputStream(fixedFormatFileName);
			sc = new Scanner(inputStream, "UTF-8");
			writer = new CSVWriter(new FileWriter(csvFileName));
			String line = null;

			String[] columnNames = new String[metaDataList.size()];
			for (int i = 0; i < metaDataList.size(); i++) {
				columnNames[i] = metaDataList.get(i)[0];
			}
			writer.writeNext(columnNames);

			while (sc.hasNextLine()) {
				line = sc.nextLine();
				int fieldLength = 0;
				int nextFileLength = 0;
				String[] csvRow = new String[metaDataList.size()];

				for (int i = 0; i < metaDataList.size(); i++) {
					String[] row = metaDataList.get(i);
					nextFileLength += Integer.parseInt(row[1]);
					String dataType = row[2];

					if ("date".equalsIgnoreCase(dataType)) {
						csvRow[i] = changeDateFormat(line.substring(fieldLength, nextFileLength).trim());
					} else if ("string".equalsIgnoreCase(dataType)) {
						csvRow[i] = line.substring(fieldLength, nextFileLength).trim();
					} else if ("numeric".equalsIgnoreCase(dataType)) {
						try {
							csvRow[i] = Double.valueOf(line.substring(fieldLength).trim()).toString();
						} catch (NumberFormatException e) {
							throw new IOException("Invalid numeric data in input file.");
						}
					}
					
					fieldLength = nextFileLength;
				}

				writer.writeNext(csvRow);
			}

			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	public static void main(String[] args) {
		String fileDirectory = System.getProperty("user.dir") + "/temp-files/";
		String metadataFilePath = fileDirectory + "metadata.csv";
		String fixedFormatFilePath = fileDirectory + "fixedfile.txt";
		String outputCsvFilePath = fileDirectory + "outputCsvFile.csv";

		try {
			new FixedFileToCsvFormatConverter().convertToCsvFile(metadataFilePath, fixedFormatFilePath, outputCsvFilePath);
		} catch (Throwable t) {
			System.out.println(t.getMessage() == null ? "" : " " + t.getMessage());
		}
	}
}