package com.mrdaydreamer.util;

public class InvalidInputException extends Exception {

	private static final long serialVersionUID = -3731961445693871886L;
	
	public InvalidInputException() {
		super();
	}
	
	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}
}
