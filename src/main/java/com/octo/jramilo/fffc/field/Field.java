package com.octo.jramilo.fffc.field;

import com.octo.jramilo.fffc.exception.InvalidFormatException;

public abstract class Field {
	String value;
	
	public Field(String value) {
		this.value = value;
	}

	public abstract String format()  throws InvalidFormatException;
}
