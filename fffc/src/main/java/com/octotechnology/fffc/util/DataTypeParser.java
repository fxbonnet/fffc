package com.octotechnology.fffc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.octotechnology.fffc.exception.FixedFileFormatParserException;
import com.octotechnology.fffc.parser.metadata.ColumnType;

public class DataTypeParser {
	private static final SimpleDateFormat FROM_FORMAT = new SimpleDateFormat("yyyy-mm-dd");
	private static final SimpleDateFormat TO_FORMAT = new SimpleDateFormat("dd/mm/yyyy");
	private static final char[] INVALID_CHARS = { '\n', '\r' };
	private static final char SEPARATOR_CHAR = ',';
	private static final char WRAPPER_CHAR = '\"';
	private static final String NUMERIC_PATTERN = "\\-?\\d*\\.?\\d+$";

	public static String parseDate(String inputDate) throws FixedFileFormatParserException {
		String formattedDate = inputDate;
		if (StringUtils.isNotBlank(inputDate)) {
			try {
				Date date = FROM_FORMAT.parse(inputDate);
				formattedDate = TO_FORMAT.format(date);
			} catch (ParseException e) {
				throw new FixedFileFormatParserException("Invalid date format '" + inputDate + "' in the input file");
			}
		}
		return formattedDate;
	}

	public static String parseString(String inputString) throws FixedFileFormatParserException {
		String formattedString = inputString;
		if (StringUtils.isEmpty(inputString)) {
			return StringUtils.EMPTY;
		}
		if (StringUtils.containsOnly(inputString, INVALID_CHARS)) {
			throw new FixedFileFormatParserException("Invalid escape character found in : " + inputString);
		}
		formattedString = formattedString.trim();
		if (StringUtils.containsAny(formattedString, SEPARATOR_CHAR)) {
			formattedString = StringUtils.wrap(formattedString, WRAPPER_CHAR);
		}
		return formattedString;
	}

	public static String parseNumeric(String inputNumeric) throws FixedFileFormatParserException {
		if (StringUtils.isBlank(inputNumeric)) {
			return StringUtils.EMPTY;
		}
		inputNumeric = inputNumeric.trim();
		if (!inputNumeric.matches(NUMERIC_PATTERN)) {
			throw new FixedFileFormatParserException("Invalid numeric data : " + inputNumeric);
		}
		return inputNumeric;
	}

	public static String parseData(String inputData, ColumnType columnType) throws FixedFileFormatParserException {
		switch (columnType) {
		case DATE:
			return DataTypeParser.parseDate(inputData);
		case STRING:
			return DataTypeParser.parseString(inputData);
		case NUMERIC:
			return DataTypeParser.parseNumeric(inputData);
		default:
			return null;
		}
	}
}
