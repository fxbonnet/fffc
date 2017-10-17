package com.fileconverter.dto.types;

import com.fileconverter.util.BLException;

public class NumberType implements ItemType{
	private static final String MESSAGE_WRONG_INPUT_NUMBER_FORMAT = "The source file line has unparsable number ";

	@Override
	public String parse(String sourceString) throws BLException {
		String result = null;
		try {
			String str = sourceString.trim();
			//next line just to validate if a number. You may use reqexp instead
			Double item = Double.parseDouble(str);
			result = str;
		} catch (Exception e) {
			throw new BLException(MESSAGE_WRONG_INPUT_NUMBER_FORMAT);
		}
		return result;
	}

}
