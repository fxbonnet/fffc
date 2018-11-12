package com.octo.fffcapp.converter;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.octo.fffcapp.exception.FixedFileFormatConverterIOException;
import com.octo.fffcapp.exception.FixedFileFormatConverterParseException;
import com.octo.fffcapp.file.FileManager;
import com.octo.fffcapp.file.FileReader;
import com.octo.fffcapp.file.FileWriter;
import com.octo.fffcapp.parser.DataFileParser;
import com.octo.fffcapp.parser.MetadataParser;

public class FFFConverter {
	private static Logger logger = LogManager.getLogger(FFFConverter.class);
	
	private FileManager fileManager;
	
	public FFFConverter() {
		this.fileManager = FileManager.getInstance();
	}
	
	public boolean process() {
		if(!checkFileLocations()) {
			return false;
		}
		
		if(!loadMetadata()) {
			return false;
		}
		
		if(!convertData()) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkFileLocations() {
		logger.info("Checking file locations.");
		
		try {
			fileManager.checkFileLocations();
		}catch(FixedFileFormatConverterIOException e) {
			logger.error("File location error.", e);
			return false;
		}
		
		return true;
	}
	
	private boolean loadMetadata() {
		Path path = fileManager.getMetadataFilePath();
		FileReader reader = new FileReader(path);
		try {
			logger.info("Attempting to parse metadata file [" + path + "].");
			reader.read(new MetadataParser());
		}catch(FixedFileFormatConverterParseException e) {
			logger.error("Unable to parse metadate file [" + path + "].", e);
			return false;
		} catch (FixedFileFormatConverterIOException e) {
			logger.error("Unable to read metadate file [" + path + "].", e);
			return false;
		}
		
		return true;
	}
	
	private boolean convertData() {
		FileReader reader = new FileReader(fileManager.getInputFilePath());
		try (FileWriter writer = new FileWriter(fileManager.getOutputFilePath())) {
			logger.info("Attempting to parse data file [" + fileManager.getInputFilePath()
			+ "] and to write int file [" + fileManager.getOutputFilePath() + "].");
			reader.read(new DataFileParser(writer));
		}catch(FixedFileFormatConverterParseException e) {
			logger.error(e);
			return false;
		} catch (FixedFileFormatConverterIOException e) {
			logger.error(e);
			return false;
		}
		
		return true;
	}
}
