package com.fffc.columns;

/**
 * Thrown if a column value does NOT follow the specification.
 */
public class DataFormatException extends RuntimeException {
	
	private static final long serialVersionUID = -3048707846863759381L;

	public DataFormatException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DataFormatException(String message) {
		super(message);
	}
	
}
