package au.com.octo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.octo.beans.FileConversionBean;
import au.com.octo.constants.FileConversionConstants;
import au.com.octo.util.helper.FileConversionHelper;
import au.com.octo.validation.FileConversionValidations;


public class FileConversion {

	private static final Logger logger = LoggerFactory.getLogger(FileConversion.class);
	
	/**
	 * This method accepts the String parameter for type of Scenario we are running
	 * @param scenario
	 */
	public void convertFixedFormatToCSV(FileConversionBean files) {
		logger.debug("Converting Fixed Data format file to CSV Format");
		
		List<Integer> columnsLength = new ArrayList<>();
		List<String> columnsType= new ArrayList<>();
		StringBuilder header = new StringBuilder("");
		
		readMetaDataInformation(files, columnsLength, columnsType, header); 
		
		logger.debug("Header with Column Names : {}", header);
		logger.debug("List for column lengths : {}", columnsLength);
		logger.debug("List for column Types : {}", columnsType);
		
		readAndConvertDataFile(files, columnsLength, columnsType, header); 
		
	}

	
	/**
	 * This method reads Metadata file based on Scenario passed to find out file location
	 * Other parameters stores the metadata information in list for each Column Length and it's type, 
	 * List avoid the recursive file read operation.
	 * @param scenario
	 * @param columnsLength
	 * @param columnsType
	 * @param header
	 */
	private void readMetaDataInformation(FileConversionBean files, List<Integer> columnsLength, 
			List<String> columnsType, StringBuilder header) {

		String metadataFile = files.getMetadataFile();
		String line = null;

		try ( InputStream in = new FileInputStream(new File(metadataFile));
				BufferedReader reader = new BufferedReader(new InputStreamReader(in)) ) {

			while ( (line = reader.readLine()) != null ) {
				String[] column = line.split(FileConversionConstants.DELIMITER.toString());
				logger.debug("MetaData Line for a column : {}", line);

				FileConversionValidations.metadataColumnFormatValidation(column);

				populateHeaderWithColumnNames(header, column);
				columnsLength.add(Integer.parseInt(column[1]));
				columnsType.add(column[2]);
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * This method checks and add delimiter(comma) only after first column populated
	 * @param header
	 * @param column
	 */
	private void populateHeaderWithColumnNames(StringBuilder header, String[] column) {
		if ( header.length() > 0 ) {
			header.append(FileConversionConstants.DELIMITER).append(column[0]);
		} else {
			header.append(column[0]);
		}
	}

	
	
	/**
	 * This method reads Data file based on Scenario passed to find out file location
	 * Other parameters stored the metadata information in list for each Column Length and it's type
	 * 1. Read each line from Data File, truncate data for each column and 
	 * 2. converted/format data based on MetaData information lists 
	 *    like date YYYY-MM-DD to dd/mm/yyyy
	 *  3. Write comma seperated data row to output file on location based on scenario
	 * @param scenario
	 * @param columnsLength
	 * @param columnsType
	 * @param header
	 */
	private void readAndConvertDataFile(FileConversionBean files, List<Integer> columnsLength,
			List<String> columnsType, StringBuilder header) {

		String fixedFormatDataFile = files.getDataFixedFormatFile(); 
		String outputFile = files.getOutputCSVFormatFile(); 

		int noOfColumns = columnsLength.size();
		String line = null;

		try ( InputStream in = new FileInputStream(new File(fixedFormatDataFile));
				OutputStream out = new FileOutputStream(new File(outputFile));
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf8"), 20 * 1024);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "utf8")) ) {

			/*
			 * BufferedInputStream bin = new BufferedInputStream(in) Reader reader = new
			 * InputStreamReader(bin)
			 */

			writer.write(header.toString());

			while ( (line = reader.readLine()) != null ) {
				logger.debug("Data row : {}", line);

				int nextStartIndex = 0;
				StringBuilder csvDataRow = new StringBuilder();

				for ( int index = 0; index < noOfColumns; index++ ) {

					int columnWidth = columnsLength.get(index);
					String columnType = columnsType.get(index);

					logger.debug("Start index : {}, Column width : {}, End index : {}", nextStartIndex, columnWidth,
							nextStartIndex + columnWidth);
					String columnValue = line.substring(nextStartIndex, nextStartIndex + columnWidth);

					nextStartIndex += columnWidth;

					populateRowDataInCSVFormat(csvDataRow, index, columnType, columnValue);
				}
				writer.newLine();
				writer.write(csvDataRow.toString());
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("Encoding not supported - {}", e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * This method based on Column type formats the data and append with delimiter<comma>
	 * @param csvDataRow
	 * @param index
	 * @param columnType
	 * @param columnValue
	 */
	private void populateRowDataInCSVFormat(StringBuilder csvDataRow, int index, 
			String columnType, String columnValue) {
		
		if ( index != 0 ) {
			csvDataRow.append(FileConversionConstants.DELIMITER)
					.append(FileConversionHelper.getFormattedColumnValue(columnValue, columnType));
		} else {
			csvDataRow.append(FileConversionHelper.getFormattedColumnValue(columnValue, columnType));
		}
	}

}
