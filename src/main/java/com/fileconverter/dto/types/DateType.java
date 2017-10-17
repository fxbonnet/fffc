package com.fileconverter.dto.types;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fileconverter.util.BLException;

public class DateType implements ItemType{
	private static final SimpleDateFormat DATE_FORMAT_INPUT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DATE_FORMAT_OUTPUT = new SimpleDateFormat("dd/MM/yyyy");
	
	private static final String MESSAGE_WRONG_INPUT_DATE_FORMAT = "Error parsing Date in the source line ";

	@Override
	public String parse(String sourceString) throws BLException {
		String result = null;
		try {
			Date date = DATE_FORMAT_INPUT.parse(sourceString);
			result = DATE_FORMAT_OUTPUT.format(date);
		} catch (Exception e) {
			throw new BLException(MESSAGE_WRONG_INPUT_DATE_FORMAT + sourceString);
		}
		return result;
	}

}
