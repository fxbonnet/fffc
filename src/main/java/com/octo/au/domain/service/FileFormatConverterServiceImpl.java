package com.octo.au.domain.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload.InvalidFileNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.octo.au.constants.Constants;
import com.octo.au.domain.model.DataRow;
import com.octo.au.domain.model.format.Structure;
import com.octo.au.domain.service.processor.contract.DataExporter;
import com.octo.au.domain.service.processor.contract.DataProcessor;
import com.octo.au.domain.service.processor.contract.TemplateProcessor;
import com.octo.au.domain.service.processor.impl.DataExporterImpl;
import com.octo.au.domain.service.processor.impl.DataProcessorImpl;
import com.octo.au.domain.service.processor.impl.TemplateProcessorImpl;
import com.octo.au.exception.CustomException;

@Service
public class FileFormatConverterServiceImpl implements FileFormatConverterService {
	private static final Logger logger = LoggerFactory.getLogger(FileFormatConverterServiceImpl.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.octo.au.service.FileFormatConverterService#writeCsvFile(java.lang.
	 * String)
	 */
	@Override
	public void writeCsvFile(String metadataFile, String dataFile) throws IOException, CustomException {
		if(StringUtils.isEmpty(metadataFile) || StringUtils.isEmpty(dataFile)){
			throw new InvalidFileNameException(StringUtils.isEmpty(metadataFile)?Constants.STR_METADATA_FILE:Constants.STR_DATA_FILE,Constants.STR_USER_MESSAGE_FILENAME_INVALID);
		}
		ClassLoader classLoader = getClass().getClassLoader();
		//TODO : change this to take the input file path from the User.Check this point for any exceptions thrown
		File file = new File(classLoader.getResource(metadataFile).getFile());
		logger.info(Constants.STR_CUSTOM_COMMENT_IDENTIFIER+Constants.STR_METADATA_FILE_READ_INITIATED);
		TemplateProcessor templateProcessor = new TemplateProcessorImpl();
		Structure metadataStructure = templateProcessor.createStructureTemplates(file);
		logger.info(Constants.STR_CUSTOM_COMMENT_IDENTIFIER+Constants.STR_METADATA_FILE_READ_COMPLETED);
		
		//TODO : change this to take the input file path from the User.Check this point for any exceptions thrown
		File dataFileObj = new File(classLoader.getResource(dataFile).getFile());
		logger.info(Constants.STR_CUSTOM_COMMENT_IDENTIFIER+"Reading Data Lines Initiated");
		DataProcessor DataProcessor = new DataProcessorImpl();
		List<DataRow> dataRows = DataProcessor.getColumnsFromDataFile(dataFileObj,metadataStructure);
		dataRows.stream().forEach(dr -> dr.showItems());
		logger.info(Constants.STR_CUSTOM_COMMENT_IDENTIFIER+Constants.STR_DATA_FILE_READ_COMPLETED);
		
		DataExporter dataExporter = new DataExporterImpl();
		dataExporter.exportData(dataRows,Constants.STR_CSV_NAME,metadataStructure.getCt());
	}
}
