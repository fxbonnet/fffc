package au.com.octo.util.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.octo.constants.FileConversionConstants;
import au.com.octo.util.FileConversionUtil;

public class FileConversionHelper {

	private static final Logger logger = LoggerFactory.getLogger(FileConversionHelper.class);
	
	private FileConversionHelper() {
		// empty constructor
	}

	/**
	 * Provides data file name and location based on scenario running
	 * @param scenario
	 * @return
	 */
	public static String getFixedFormatDataFileName(String scenario, String baseLocation) {
		
		return baseLocation
				+ FileConversionConstants.INPUT_FILE_LOCATION + FileConversionConstants.DATA_FILE + scenario
				+ FileConversionConstants.TEXT_FILE_TYPE;
	}
	
	/**
	 * Provides meta data file name and location based on scenario running
	 * @param scenario
	 * @return
	 */
	public static String getMetaDataFileName(String scenario, String baseLocation) {
		
		return baseLocation
				+ FileConversionConstants.INPUT_FILE_LOCATION + FileConversionConstants.METADAT_FILE + scenario
				+ FileConversionConstants.CSV_FILE_TYPE;
	}
	
	/**
	 * Provides output csv file name and location based on scenario running
	 * @param scenario
	 * @return
	 */
	public static String getOutputFileName(String scenario, String baseLocation) {
		
		return baseLocation
				+ FileConversionConstants.OUTPUT_FILE_LOCATION + FileConversionConstants.OUTPUT_FILE + scenario
				+ FileConversionConstants.CSV_FILE_TYPE;
	}
	
	/**
	 * This method format the data for column according to it's type.
	 * Date -> date string from yyyy-MM-dd  >> dd/MM/yyyy
	 * String -> if ',' present then escape using double quotes "
	 * @param columnValue
	 * @param columnType
	 * @return
	 */
	public static  String getFormattedColumnValue(String columnValue, String columnType) {
		StringBuilder formattedValue = null;
		
		if ( columnValue != null ) {
			
			if ( FileConversionConstants.STRING.equalsIgnoreCase(columnType) ) {
				formattedValue = getStringTypeData(columnValue);
			} else if ( FileConversionConstants.NUMERIC.equalsIgnoreCase(columnType) ) {
				formattedValue =  new StringBuilder(columnValue.trim());
			} else if ( FileConversionConstants.DATE.equalsIgnoreCase(columnType) ) {
				formattedValue =  new StringBuilder(FileConversionUtil.convertDateFormat(columnValue));
			} 
		}
		logger.debug("Data for a Column[{}] after formatting :{}", columnValue, formattedValue);
		return formattedValue != null ? formattedValue.toString() : FileConversionConstants.EMPTY;
	}

	/**
	 * String -> if ',' present then escape using double quotes "
	 * @param columnValue
	 * @return
	 */
	private static StringBuilder getStringTypeData(String columnValue) {
		
		StringBuilder formattedValue =  new StringBuilder(columnValue.trim());
		
		if (formattedValue.indexOf("\n") >= 0 || formattedValue.indexOf("\r") >= 0) {
			throw new RuntimeException("Invalid Data format exception");
		}
		
		// Add Escape Quote to String type data if ',' is present
		if ( formattedValue.indexOf(FileConversionConstants.DELIMITER.toString()) >= 0 ) {
			formattedValue.insert(0, FileConversionConstants.QUOTE).append(FileConversionConstants.QUOTE);
		}
		return formattedValue;
	}
}
