package com.octo.fffcapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.octo.fffcapp.converter.FFFConverter;
import com.octo.fffcapp.file.FileManager;

/**
 * generic tool to convert fixed file format files to a csv file 
 * based on a metadata file describing its structure.
 * <p>
 * Fixed file format files can have any number of columns A column 
 * can be of 3 formats:
 * <ul>
 * <li>date (format yyyy-mm-dd)
 * <li>numeric (decimal separator '.' ; no thousands separator ; 
 * can be negative)
 * <li>string
 * </ul>
 * <p>
 * The structure of the file is described in a metadata file in csv 
 * format with a line for each column defining:
 * <ul>
 * <li>column name
 * <li>column length
 * <li>column type
 * </ul>
 * 
 * @author      Sheikh Hasan
 */
public class FFFConverterApplication {
	private static Logger logger = LogManager.getLogger(FFFConverterApplication.class);
	
    public static void main(String[] args ) {
        if(args == null || args.length != 3) {
        	logger.fatal("Input parameters missing. Mandatory input parameters: "
        			+ "1. Input File Path, "
        			+ "2. Metadata File Path, "
        			+ "3. Output File Path. The sequence should be in correct order.");
        	logger.info("Exiting...");
        	return;
        }
        
        FileManager fileManager = FileManager.getInstance();
        fileManager.setInputFilePath(args[0]);
        fileManager.setMetadataFilePath(args[1]);
        fileManager.setOutputFilePath(args[2]);
        
        FFFConverter converter = new FFFConverter();
        boolean isSuccessful = converter.process();
        if(isSuccessful) logger.info("Done.");
        else logger.info("Exiting...");
    }
}
