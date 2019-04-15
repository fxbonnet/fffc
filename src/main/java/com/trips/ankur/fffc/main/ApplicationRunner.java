package com.trips.ankur.fffc.main;

import com.trips.ankur.fffc.data.DataParserAndWriter;
import com.trips.ankur.fffc.exceptions.NotEnoughArgumentException;
import com.trips.ankur.fffc.metadata.MetaData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This is the main class. It starts the Application.
 * 
 * @author tripaank
 *
 */
public class ApplicationRunner {

	private static String metaDataFile;
	private static String dataFile;
	private static DataParserAndWriter parser;
	private static String outPutFileName;
	private static boolean processWithError = false;
	private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);


	/**
	 * Main Method.
	 * arg1 - Metadata File Path
	 * arg2 - Data File path
	 * arg3 - Output File Path
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("[Main] Application started.");
		
		if (args.length < 3) {
			logger.error("Not Enough Arguments passed...");
			logger.error("Usage Instructions :");
			logger.error("Please provide 3 arugments as below.");
			logger.error("Argument 1: Metadata File Complete Path.");
			logger.error("Argument 2: Data File Complete Path.");
			logger.error("Argument 3: Output File Complete Path.");
			logger.error("Argument 4: Optional Argument, value = true/false, pass it as true if the data needs to be read without terminating the process and Error file needs to be created with error records.");
			throw new NotEnoughArgumentException("Not Enough Arguments Passed to the application - List of Arguments are : "+args);
		}
		validateArguements(args);
		try {
			parser = new DataParserAndWriter(FileUtils.getFile(metaDataFile), FileUtils.getFile(dataFile));
			MetaData metaData = parser.parseMetaData();
			parser.parseDataAndWriteOutput(metaData,outPutFileName, processWithError);
			logger.info("[Main] CSV File Written to : " + outPutFileName);

		} catch (Exception e) {
			logger.error("Exception Occured: ", e);
			e.printStackTrace();
		}
	}

	/**
	 * This method Validates the arguments.  
	 * 
	 * @param args
	 */
	private static void validateArguements(String[] args) {
		metaDataFile = StringUtils.defaultString(args[0]);
		dataFile = StringUtils.defaultString(args[1]);
		outPutFileName = StringUtils.defaultString(args[2]);
		if(args.length == 4)
			processWithError = StringUtils.defaultString(args[3]).equals("true")  ? true  : false;
		if (StringUtils.isAnyBlank(metaDataFile, dataFile, outPutFileName)) {
			logger.error("[validateArguements] All three manadatory parameters must be specified.");
			logger.warn("[validateArguements] metaDataFile = " + metaDataFile);
			logger.warn("[validateArguements] dataFile = " + dataFile);
			logger.warn("[validateArguements] outPutFileName = " + outPutFileName);
			logger.warn("[validateArguements] processWithError = " + processWithError);
			throw new NotEnoughArgumentException("[validateArguements] Empty Arguments passed - List of Arguments are : "+args);
		}
	}
}
