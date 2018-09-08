import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProcessDataFile {
	private static final String COLUMN_DELIMITER = ",";
	private static final String ENDOFLINE_CHARACTER = "\n";
	private static final String ENCODING = "UTF-8";

	private MetaDataDO metaDataDO;

	private Map<String, Integer> columnDataLengthMap = new LinkedHashMap<String, Integer>();
	private Map<String, String> columnDataTypeMap = new LinkedHashMap<String, String>();
	private String destinationLocation;
	private String dataFileLocation;
	private int totalLineLength = 0;
	private String newCSVFileName;
	boolean isHeaderWritten = false;

	private BufferedWriter csvFileWriter = null;

	public ProcessDataFile(String dataFileLocation, String destinationLocation,
			MetaDataDO metaDataDO) {
		this.dataFileLocation = dataFileLocation;
		this.destinationLocation = destinationLocation;
		this.metaDataDO = metaDataDO;

		// Set the required maps
		columnDataLengthMap = this.metaDataDO.getColumnDataLengthMap();
		columnDataTypeMap = this.metaDataDO.getColumnDataTypeMap();
		totalLineLength = this.metaDataDO.getTotalLengthPerLine();
	}

	public String processDataFile() {

		BufferedReader brData = null;
		// Split indicator set to comma ,
		int lineCount = 0;

		try {
			brData = new BufferedReader(new InputStreamReader(
					new FileInputStream(dataFileLocation), ENCODING));
			String currLine = "";
			while ((currLine = brData.readLine()) != null) {
				lineCount++;
				processDataLine(new StringBuffer(currLine), lineCount);
			}
			
			if (lineCount == 0) {
				throw new RuntimeException("The Data file is empty. No data available to convert into CSV.");
			}

			try {
				if (csvFileWriter != null) {
					csvFileWriter.flush();
					csvFileWriter.close();
				}

			} catch (IOException en) {
				throw new RuntimeException("Unexpected error occured while flushing/closing the CSV File Writer.");
			}
		} catch (IOException e) {
			throw new RuntimeException("Error during accessing/reading the data file in path: "
					+ newCSVFileName);
		} finally {
			try {
				brData.close();
			} catch (IOException e) {
				throw new RuntimeException("Error while closing BufferedReader for the data file.");
			}
		}

		// If all goes well, return the csv file name
		return newCSVFileName;
	}

	/** This method processes each line of data from the data file. */
	private void processDataLine(StringBuffer currLine, int lineCount) {
		List<String> dataToWrite = null;

		// If the line does not meet the total length requirement, throw an
		// exception
		if (currLine == null || currLine.length() != totalLineLength) {
			throw new RuntimeException(
					"Data file cannot be processed. Failure at line number: "
							+ lineCount
							+ ". Length of each line is expected to be "
							+ totalLineLength + ".");
		}

		// Iterate through the columnDataLength linkedhashmap
		for (String currColumnHeader : columnDataLengthMap.keySet()) {
			// Get the expected data length for the current column header.
			int currLength = columnDataLengthMap.get(currColumnHeader);
			String currDataType = columnDataTypeMap.get(currColumnHeader);

			String data = currLine.substring(0, currLength).trim();
			currLine = currLine.delete(0, currLength);

			// If data type is date, validate and convert date
			if (currDataType.equalsIgnoreCase("date")) {
				data = getValidatedAndFormattedDate(data, lineCount);
			} else if (currDataType.equalsIgnoreCase("numeric")) {
				validateNumeric(data, lineCount);
			} else if (currDataType.equalsIgnoreCase("string")) {
				// Add quotes around the string to eliminate issues with possible commas in the string.
				data = "\"" + data + "\"";
			}

			if (dataToWrite == null) {
				dataToWrite = new ArrayList<String>();
			}
			dataToWrite.add(data);
		}
		// write the data into the file.
		// Write the header into the file
		if (!isHeaderWritten) {
			writeIntoCSVFile(new ArrayList<String>(columnDataLengthMap.keySet()));
			isHeaderWritten = true;
		}

		writeIntoCSVFile(dataToWrite);
	}

	/**
	 * Method to write into the CSV file. If the file does not exist, it is
	 * created. The input string array is iterated through and appended with a
	 * comma separator. The
	 */
	private void writeIntoCSVFile(List<String> writingData) {
		try {
			// Create the file if it isn't created already.
			if (csvFileWriter == null) {
				if ((destinationLocation!=null)&&(destinationLocation.length()>2) && ((destinationLocation
						.charAt(destinationLocation.length() - 1) == '\\')
						|| (destinationLocation.charAt(destinationLocation
								.length() - 1) == '/'))) {
					
					destinationLocation = destinationLocation.replace(
							destinationLocation.substring(destinationLocation
									.length() - 1), "");
					
				}
				newCSVFileName = destinationLocation + "\\ConvertedCSVFile_"
						+ new Date().getTime() + ".csv";
				csvFileWriter = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(newCSVFileName), ENCODING));
			}

			// Iterate through the writingData array list and write into the
			// file.
			for (String appendData : writingData) {
				csvFileWriter.append(String.valueOf(appendData));
				// append the column delimiter
				csvFileWriter.append(COLUMN_DELIMITER);
			}

			// append the column delimiter
			csvFileWriter.append(ENDOFLINE_CHARACTER);

		} catch (Exception e) {
			try {
				csvFileWriter.flush();
				csvFileWriter.close();
			} catch (IOException en) {
				throw new RuntimeException("Unexpected error occured while flushing/closing the CSV File Writer.");
			}
			throw new RuntimeException("Unexpected error in writing into the CSV file.");
		}
	}

	/**
	 * Method to validate the date string. Convert the date to a new format for
	 * the csv file and return.
	 */
	private String getValidatedAndFormattedDate(String data, int lineCount) {
		Date oldDate;
		try {
			oldDate = new SimpleDateFormat("yyyy-mm-dd").parse(data);
		} catch (ParseException e) {
			throw new RuntimeException(
					"The date "
							+ data
							+ " on line "
							+ lineCount
							+ " did not meet the standard date format yyyy-mm-dd. The data file cannot be processed.");
		}
		// Convert to the csv date format.
		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/mm/yyyy");
		return newDateFormat.format(oldDate);

	}

	/**
	 * Method to validate the date string. Convert the date to a new format for
	 * the csv file and return.
	 */
	private void validateNumeric(String data, int lineCount) {
		try {
			new BigInteger(data);
		} catch (NumberFormatException e) {
			try {
				new BigDecimal(data);
			} catch (NumberFormatException ne) {
				throw new RuntimeException(
						"The numeric: "
								+ data
								+ " on line "
								+ lineCount
								+ " did not meet the standard numeric format. The data file cannot be processed. ");
			}
		}
	}

}
