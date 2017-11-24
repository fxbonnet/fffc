package com.octo.jramilo.fffc.exception;

public class InvalidFileExpection extends Exception {

	private static final long serialVersionUID = 8665353029116015902L;

	public InvalidFileExpection() {
		super();
	}

	public InvalidFileExpection(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidFileExpection(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidFileExpection(String message) {
		super(message);
	}

	public InvalidFileExpection(Throwable cause) {
		super(cause);
	}
	
}
