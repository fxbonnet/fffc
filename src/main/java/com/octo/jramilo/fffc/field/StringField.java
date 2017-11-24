package com.octo.jramilo.fffc.field;

import com.octo.jramilo.fffc.exception.InvalidFormatException;

public class StringField extends Field {

	public StringField(String value) {
		super(value);
	}

	@Override
	public String format() throws InvalidFormatException{
		value = value.trim();
		if(value.contains(",")) {
			value = "\"" + value + "\"";
		}
		return value;
	}

}
