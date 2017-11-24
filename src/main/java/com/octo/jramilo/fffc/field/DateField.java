package com.octo.jramilo.fffc.field;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.util.Constant;

public class DateField extends Field {
	
	@Override
	public String format(String value) throws InvalidFormatException {
		Date date;
		try {
			date = new SimpleDateFormat(Constant.DATE_FORMAT_ORIG).parse(value);
		} catch (ParseException e) {
			throw new InvalidFormatException(e);
		}
		SimpleDateFormat sFormat = new SimpleDateFormat(Constant.DATE_FORMAT_NEW);
		return sFormat.format(date);
	}

}
