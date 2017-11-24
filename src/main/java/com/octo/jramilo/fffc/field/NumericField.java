package com.octo.jramilo.fffc.field;

import com.octo.jramilo.fffc.exception.InvalidFormatException;

public class NumericField extends Field {

	@Override
	public String format(String value) throws InvalidFormatException {
		return value.trim();
	}

}
