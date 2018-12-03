package com.fileConvert;

import java.util.Date;
import java.util.List;
import com.common.CustomMessage;
import com.exception.CustomException;
import com.model.EnumType;
import com.model.MetaData;

/*
 * This class validates lines read from text file and formats the text that will be written in CSV file.
 */

public class TextFileReader {

	

	/**
	 * This method validates the text data line by line and format the text that
	 * need to be written in CSV.
	 * 
	 * @param nextLine        A variable of type String.
	 * @param metadataValues a variable of type list
	 * @return String.
	 * @exception CustomException On error.
	 */

	public String parseTextFile(String nextLine, List<MetaData> metadataValues) throws CustomException {
		
		StringBuffer txtAppender = new StringBuffer();
		try {
		for (int i = 0; i < metadataValues.size(); i++) {
			String data = nextLine.substring(0, metadataValues.get(i).getLength());

			txtAppender = getFormattedText(data, metadataValues.get(i).getType(), txtAppender);
			if (i < metadataValues.size() - 1)
				txtAppender.append(CustomMessage.COMMA);
			nextLine = nextLine.substring(metadataValues.get(i).getLength());
		}
		}catch(Exception e) {
			throw new CustomException(CustomMessage.INVALID_DATA);
		}
		return txtAppender.append(CustomMessage.NEW_LINE).toString().trim();
	}

	private StringBuffer stringFormat(String data, StringBuffer txtAppender) {
		if (data.contains(",")) {
			data = data.trim();
			return txtAppender.append("\"" + data + "\"");
		}
		return txtAppender.append(data.trim());
	}

	private StringBuffer dateFormat(String data, StringBuffer txtAppender) throws CustomException {

		try {
			Date date = CustomMessage.TXT_DATE_FORMAT.parse(data);
			return txtAppender.append(CustomMessage.CSV_DATE_FORMAT.format(date));
		} catch (Exception e) {
			throw new CustomException(CustomMessage.DATE_PARSE_EXCEPTION);
		}
	}

	private StringBuffer decimalFormat(String data, StringBuffer txtAppender) throws CustomException {

		try {
			return txtAppender.append(Float.parseFloat(data));
		} catch (NumberFormatException e) {
			throw new CustomException(CustomMessage.INVALID_WEIGHT);

		}

	}
	private StringBuffer getFormattedText(String data, EnumType type, StringBuffer txtAppender)
			throws CustomException {
		switch (type) {
		case STRING:
			return stringFormat(data, txtAppender);
		case DATE:
			return dateFormat(data, txtAppender);
		case NUMERIC:
			return decimalFormat(data, txtAppender);
		default:
			return null;
		}
	}

}
