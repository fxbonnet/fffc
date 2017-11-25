package com.octo.jramilo.fffc.field;

import org.apache.commons.lang3.math.NumberUtils;

import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.util.ErrorMessage;

public class NumericField extends Field {

	@Override
	public String format(String value) throws InvalidFormatException {
		value = value.trim();
		if(!NumberUtils.isCreatable(value)) {
			throw new InvalidFormatException(ErrorMessage.WEIGHT_NOT_NUMERIC);
		}
		
		return value;
	}

}
