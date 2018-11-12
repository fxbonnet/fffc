package com.octo.fffcapp.exception;

public class FixedFileFormatConverterParseException extends RuntimeException {	
	/**
	 * These exceptions are thrown during file operations.
	 */
	private static final long serialVersionUID = 1L;
	
	public FixedFileFormatConverterParseException() {
		super();
	}

	public FixedFileFormatConverterParseException(String string, Exception e) {
		super(string, e);
	}
	
	public FixedFileFormatConverterParseException(Exception e) {
		super(e);
	}
	
	public FixedFileFormatConverterParseException(String message) {
		super(message);
	}
}
