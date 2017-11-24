package com.octo.jramilo.fffc.field;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.octo.jramilo.fffc.exception.InvalidFormatException;

public class DateField extends Field {

	public DateField(String value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String format() throws InvalidFormatException {
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
		} catch (ParseException e) {
			throw new InvalidFormatException(e);
		}
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy/MM/dd");
		return sFormat.format(date);
	}

}
