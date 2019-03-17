package com.octo.code.practice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.octo.code.practice.exception.CSVConverterCustomizedException;

public class DataParserUtils {
	public static String stringTypeFormat(String string, int rowIndex) {
		if (string != null) {
			String formattedString = null;
			formattedString = string.trim();
			if (formattedString.indexOf(CSVConverterUtils.COMMA_DELIMITER) != -1) {
				formattedString = "\"" + formattedString + "\"";
			}
			return formattedString;
		}
		return null;
	}
	
	public static String dateTypeFormat(String inputDateString, int rowIndex) {
		String formattedDateString = null;
		String inputDateStringPattern = null;
		String formattedDatePattern = null;
		try {
			inputDateStringPattern = "yyyy-MM-dd";
			formattedDatePattern = "dd/MM/yyyy";
			SimpleDateFormat sdf1 = new SimpleDateFormat(inputDateStringPattern);
			SimpleDateFormat sdf2 = new SimpleDateFormat(formattedDatePattern);
			Date formattedDate = sdf1.parse(inputDateString);
			formattedDateString = sdf2.format(formattedDate);
		} catch (ParseException e) {
			String errorMessage = "The date type data:[" + inputDateString + "] cannot be recognized as a valid date by follwoing format:" + inputDateStringPattern + " - row index:" + rowIndex;
			throw new CSVConverterCustomizedException(errorMessage);
		}
		return formattedDateString;
	}
	
	public static String numbericTypeFormat(String intputNumberString, int rowIndex) {
		double formattedNumber = 0.0;
		try {
			formattedNumber = Double.parseDouble(intputNumberString.trim());
		} catch(NumberFormatException e) {
			String errorMessage = "The data: [" + intputNumberString + "] is not a valid numberic value - $row index:" + rowIndex;
			throw new CSVConverterCustomizedException(errorMessage);
		}
		return String.valueOf(formattedNumber);
	}
}
