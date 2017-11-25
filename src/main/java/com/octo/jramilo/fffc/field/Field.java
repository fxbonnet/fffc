package com.octo.jramilo.fffc.field;

import com.octo.jramilo.fffc.exception.InvalidFormatException;

public abstract class Field {
	
	public abstract String format(final String value)  throws InvalidFormatException;
	
}
