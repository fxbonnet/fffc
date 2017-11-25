package com.octo.jramilo.fffc.field;

import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.util.Constant;

public class StringField extends Field {

	@Override
	public String format(String value) throws InvalidFormatException {
		value = value.trim();
		if(value.contains(Constant.COMMA)) {
			value = Constant.DOUBLE_QUOTE_ESCAPED + value + Constant.DOUBLE_QUOTE_ESCAPED;
		}
		return value;
	}

}
