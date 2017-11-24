package com.octo.jramilo.fffc.field;

import com.octo.jramilo.fffc.exception.InvalidFormatException;

public class NumericField extends Field {

	public NumericField(String value) {
		super(value);
	}

	@Override
	public String format() throws InvalidFormatException{
		return value.trim();
	}

}
