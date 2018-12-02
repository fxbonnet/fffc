package fileConvert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/* The  CustomException Message class.
* 
* @author Ankush
*/

public class CustomMessage {
	// CSV date format
	public static final SimpleDateFormat CSV_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	// Text file date format
	public static final DateFormat TXT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final String DELIMITER = ",";
	public static final String NEW_LINE = "\n";
	public static final String TEXT_FILE_NOT_FOUND = "ERROR:Input text file not found.";
	public static final String CSV_FILE_NOT_FOUND = "ERROR:Output CSV file not found.";
	public static final String HEADER_FORMAT = "Birth date,First name,Last name,Weight \n";
	public static final String IOEXCEPTION = "ERROR:Failed or interrupted I/O operations.";
	public static final String RESOURCE_EXCEPTION = "ERROR:Error closing the file resources.";
	public static final String DATE_PARSE_EXCEPTION = "ERROR:Error parsing the date.";
	public static final String INVALID_FIRST_NAME = "ERROR:Invalid data of First Name";
	public static final String INVALID_LAST_NAME = "ERROR:Invalid data of last Name";

	public static final String INVALID_WEIGHT = "ERROR:Invalid data of Weight";
	public static final String INVALID_DATA = "ERROR:Incorrect data found while reading text file.";
	public static final String TEXT_FILE_PATH = "Please enter the path of text file along with file name:";
	public static final String TEXT_FILE_PATH_ENTERED="Text File path entered : " ;
	public static final String CSV_FILE_PATH = "Please enter the path of CSV file to be created along with file name:";
	public static final String CSV_FILE_PATH_ENTERED = "CSV file path entered : ";
	public static final String INVALID_PATH = "ERROR:Please provide a valid file path :";
	public static final String CONVERT_FILE_COMPLETED = "Fixed file format files to a csv file is completeted.";

}
