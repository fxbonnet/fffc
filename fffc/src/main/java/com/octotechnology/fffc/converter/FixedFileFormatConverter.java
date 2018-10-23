package com.octotechnology.fffc.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.octotechnology.fffc.ConfigData;
import com.octotechnology.fffc.exception.FixedFileFormatException;
import com.octotechnology.fffc.exception.FixedFileFormatParserException;
import com.octotechnology.fffc.parser.MetaDataParser;
import com.octotechnology.fffc.parser.metadata.ColumnData;
import com.octotechnology.fffc.util.DataTypeParser;
import com.octotechnology.fffc.writer.CSVWriter;

/**
 * The main class of the fixed file format converter that implements the file
 * format converter contract.
 */

public class FixedFileFormatConverter implements FileFormatConverter {
	public static final Logger LOG = Logger.getLogger(FixedFileFormatConverter.class);
	List<ColumnData> columnData;
	MetaDataParser metadataParser;
	CSVWriter csvWriter;
	ConfigData configData;

	public void run(ConfigData configData) {
		this.configData = configData;
		try {
			initialize();
			convert();
			cleanup();
		} catch (FixedFileFormatException e) {
			e.printStackTrace();
		}
		
	}

	public void initialize() throws FixedFileFormatException {
		LOG.trace("Initializing meta data");
		metadataParser = new MetaDataParser();
		try {
			columnData = metadataParser.parse(configData.getMetaDataFilePath());
		} catch (FixedFileFormatParserException e) {
			throw new FixedFileFormatException("Failed to initialize : "+e.getMessage());
		}
		csvWriter = new CSVWriter(columnData, configData.getOutPutFilePath());
	}

	public void convert() {
		LOG.trace("Converting input file to output format");
		File file = new File(configData.getInputFilePath());
		Scanner scanner = null;
		try {
			csvWriter.init();
			csvWriter.writeHeader();
			scanner = new Scanner(file, StandardCharsets.UTF_8.name());
			while (scanner.hasNext()) {
				List<String> elements = parseLine(scanner.nextLine());
				csvWriter.writeLine(elements);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FixedFileFormatException e) {
			e.printStackTrace();
		} finally {
			if (!Objects.isNull(scanner))
				scanner.close();
		}
	}

	private List<String> parseLine(String line) throws FixedFileFormatParserException {
		LOG.trace("Parsing input line " + line);
		int beginIndex = 0;
		int endIndex = 0;
		List<String> elements = new ArrayList<String>();
		if (line == null || line.isEmpty()) {
			return elements;
		}
		boolean exitLoop = false;
		for (ColumnData columnData : columnData) {
			beginIndex = endIndex;
			endIndex = beginIndex + columnData.getLength();
			if (line.length() < endIndex) {
				endIndex = line.length() - 1;
				exitLoop = true;
			}
			elements.add(DataTypeParser.parseData(line.substring(beginIndex, endIndex), columnData.getColumnType()));
			if (exitLoop) {
				break;
			}
		}
		LOG.trace("Parsed values :" + String.join(",", elements));
		return elements;
	}

	@Override
	public void cleanup() {
		LOG.trace("Cleaning up");
		csvWriter.close();
	}
}
