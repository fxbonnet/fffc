package com.fileconverter.service;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fileconverter.dto.SourceFileItemStructure;
import com.fileconverter.util.BLException;

public class LineConverter {
	private static final String END_OF_LINE = "\n";
	private static final String LINE_ITEMS_SPLITTER = ",";
	private static final String LINE_ITEMS_SEPARATOR = ",";
	
	// TODO:Sergey move messages into the message file
	private static final String MESSAGE_EMPTY_LINE = "The file with input structure contains empty line";
	private static final String MESSAGE_WRONG_FORMAT = "The file with input structure contains line of wrong format";
	private static final String MESSAGE_WRONG_LENGTH_FORMAT = "The file with input structure contains line with unparsable length number";
	private static final String MESSAGE_WRONG_INPUT_LENGTH = "The source line is shorter than expected";
	
	
	

	public SourceFileItemStructure parseInputStructure(String line) throws BLException {
		String errorMessage = null;
		if (!StringUtils.isBlank(line)) {
			String[] items = line.split(LINE_ITEMS_SPLITTER);
			if (items.length == 3) {
				try {
					int itemLength = Integer.parseInt(items[1].trim());
					if(itemLength > 0) {
						return new SourceFileItemStructure(items[0], 
								itemLength, 
								ItemTypeFc.getType(items[2]));	
					}else {
						errorMessage = MESSAGE_WRONG_LENGTH_FORMAT;
					}
					
				} catch (NumberFormatException e) {
					errorMessage = MESSAGE_WRONG_LENGTH_FORMAT;
				} catch (BLException e) {
					errorMessage = e.getMessage();
				}
			} else {
				errorMessage = MESSAGE_WRONG_FORMAT;
			}
		} else {
			errorMessage = MESSAGE_EMPTY_LINE;
		}
		throw new BLException(errorMessage + ". Parsed line: " + line);
	}

	

	public String parseSourceLine(String sourceLine, List<SourceFileItemStructure> items) throws BLException {
		StringBuilder sb = new StringBuilder();
		int beginIndex = 0;
		for (SourceFileItemStructure itemStructure : items) {
			if (sb.length() > 0) {
				sb.append(LINE_ITEMS_SEPARATOR);
			}
			int endIndex = beginIndex + itemStructure.getLength();
			if (sourceLine.length() >= endIndex) {
				String substrToProcess = sourceLine.substring(beginIndex, endIndex);
				sb.append(itemStructure.getType().parse(substrToProcess));
			} else {
				throw new BLException(MESSAGE_WRONG_INPUT_LENGTH);
			}
			beginIndex = endIndex;
		}
		sb.append(END_OF_LINE);
		return sb.toString();
	}

	public String parseHeader(List<SourceFileItemStructure> structure) {
		StringBuilder sb = new StringBuilder();
		for (SourceFileItemStructure inputFileStructure : structure) {
			if (sb.length() > 0) {
				sb.append(LINE_ITEMS_SEPARATOR);
			}
			sb.append(inputFileStructure.getName());
		}
		sb.append(END_OF_LINE);
		return sb.toString();
	}

}
