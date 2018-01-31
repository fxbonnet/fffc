package au.com.octo.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileConversionConstants {

	/* Constants for locating different files*/
	//public static final String 			BASE_FILE_LOCATION 			    = 		"src/main/resources/"
	public static final String 			INPUT_FILE_LOCATION 			= 		"input/";
	public static final String 			OUTPUT_FILE_LOCATION 		= 		"output/";

	/* Types of different files*/
	public static final String 			METADAT_FILE 		   	= 		"MetaDataFile_";
	public static final String 			DATA_FILE 						= 		"DataFile_";
	public static final String 			OUTPUT_FILE				= 		"Output_";

	/* File Extensions*/
	public static final String 			TEXT_FILE_TYPE 			= 		".txt";
	public static final String 			CSV_FILE_TYPE 			= 		".csv";

	/* Column Type*/
	public static final String 			STRING 			= 		"string";
	public static final String 			DATE 				= 		"date";
	public static final String 			NUMERIC 		= 		"numeric";
	
	/* Special Characters check*/
	public static final Character 	DELIMITER 	= 		',';
	public static final Character 	QUOTE 				= 		'"';
	
	public static final List<String> DATA_TYPE = Collections.unmodifiableList(
			Arrays.asList(STRING, DATE, NUMERIC));
	
	public static final String 			EMPTY				=		"";
	
	private FileConversionConstants() {
		//empty constructor
	}
}
