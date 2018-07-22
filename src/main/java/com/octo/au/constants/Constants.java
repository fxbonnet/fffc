/**
 * 
 */
package com.octo.au.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Amol Kshirsagar
 *
 */
public class Constants {
	public static String STR="String";
	public final static Charset ENCODING = StandardCharsets.UTF_8;
	public static final String STR_DELIMITER_METADATA_FILE = ",";  
	public static final String STR_CUSTOM_COMMENT_IDENTIFIER="######";
	public static final String STR_METADATA_FILE = "Metadata File";
	public static final String STR_DATA_FILE = "Data File";
	public static final String STR_USER_MESSAGE_FILENAME_INVALID = "Please check the input files for valid names";
	public static final String STR_USER_MESSAGE_NO_NEXTLINE_DATAFILE = "Empty or invalid line in Data File. Unable to process.";
	public static final String STR_USER_MESSAGE_NO_NEXTLINE_METADATAFILE = "Empty or invalid line in Data File. Unable to process.";
	public static final String STR_METADATA_FILE_READ_COMPLETED = "Meta data file Read Completed";
	public static final String STR_METADATA_FILE_READ_INITIATED = "Meta data file Read Initiated";
	public static final String STR_DATA_FILE_READ_COMPLETED = "Data file Read Completed";
	public static final String STR_CSV_NAME = "OctoData.csv";
	public static final String STR_DATA_INCOMPLETE = "Incomplete or Missing Data";
}
