package com.octo.jramilo.fffc.exception;

public class InvalidFormatException extends Exception {

	private static final long serialVersionUID = -2666123921750184996L;

	public InvalidFormatException() {
		super();
	}

	public InvalidFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidFormatException(String message) {
		super(message);
	}

	public InvalidFormatException(Throwable cause) {
		super(cause);
	}
	
}
