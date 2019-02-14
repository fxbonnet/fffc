import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.FileConvertorException;
import model.CsvMetaData;
import service.CsvMetaDataReader;
import service.FixedFileFormatProcessor;

/**
 * This is the main class to start the conversion process. 
 * It accepts the user provided arguments and kicks off the CSV File conversion.
 *   
 * @author jajalvm
 *
 */
public class Main {

	private final static Logger logger = LoggerFactory.getLogger(Main.class);
	
	/**
	 * Print the usage of this fixed file format conversion utility.
	 */
	public static void PrintUsage() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nUsage: <Data File Name> <Metadata File Name> <Output Csv File Name> \n");
		sb.append("<Data File Name>       - The fixed format data file to be processed \n");
		sb.append("<Metadata File Name>   - The metadata file describing the structure of data file\n");
		sb.append("<Output Csv File Name> - The file name of the output csv file name \n\n");
		
		sb.append("Note - Absolute file path names are supported. \n");
		sb.append("       Arguments cannot be null, empty or blank. \n");
		sb.append("       Data file and Metadata files must be present to proceed with successful conversion. \n");
		
		// logger.info(sb.toString());
		System.out.println(sb.toString());
	}
	
	/**
	 * Validates the user provided arguments. 
	 * Validation includes null, empty and blank checks for given arguments. 
	 * Also the existence of files verified
	 *   
	 * @param args  user provided args
	 * @throws IllegalArgumentException thrown when validation fails
	 */
	public static void ValidateArgs(String[] args) throws IllegalArgumentException {
		
		logger.debug("Arguments - [" +  Arrays.toString(args) + "]");
		
		// we need at least three args
		if (args.length < 3) {
			throw new IllegalArgumentException ("Insufficient arguments...");
		}
		
		if (StringUtils.isAllBlank(args)) {
			throw new IllegalArgumentException ("Valid arguments are required.");
		}
		
		// verify that the data file exist
		File dataFile = new File(args[0]);
		if (!dataFile.exists()) {
			throw new IllegalArgumentException ("Data file \"" + args[0] + "\" not found.");
		}
		
		// verify that the metadata file exist
		File metadataFile = new File(args[1]);
		if (!metadataFile.exists()) {
			throw new IllegalArgumentException ("Metadata file \"" + args[1] + "\" not found.");
		}
		
	}
	
	/**
	 * Starting entry point to run the utility.
	 *  
	 * @param args  user provided arguments
	 */
	public static void main(String[] args) {
		
		try {
			// validate args
			Main.ValidateArgs(args);
			
			// all the args are valid
			final String dataFile = args[0];
			final String metadataFile = args[1];
			final String outputFile = args[2];
			
			
			FixedFileFormatProcessor fixedFileFormatProcessor = new FixedFileFormatProcessor();
			fixedFileFormatProcessor.setInputFile(dataFile);
			fixedFileFormatProcessor.setOutputFile(outputFile);
			
			CsvMetaDataReader csvMetaDataReader = new CsvMetaDataReader();
			List<CsvMetaData> metaDataList;
			try {
				metaDataList = csvMetaDataReader.readMetaDataFile(metadataFile);
				fixedFileFormatProcessor.processInputFile(metaDataList);
				
			} catch (FileConvertorException | IOException e) {
				e.printStackTrace();
			}			
			
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			Main.PrintUsage();
		} 
		
	}

}
