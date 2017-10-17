package com.fileconverter.util;

public class BLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BLException() {

	}

	public BLException(String message) {
		super(message);
	}

	public BLException(Throwable cause) {
		super(cause);
	}

	public BLException(String message, Throwable cause) {
		super(message, cause);
	}

	public BLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
