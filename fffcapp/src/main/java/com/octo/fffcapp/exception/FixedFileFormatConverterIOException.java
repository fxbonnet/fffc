package com.octo.fffcapp.exception;

import java.io.IOException;

public class FixedFileFormatConverterIOException extends RuntimeException {	
	/**
	 * These exceptions are thrown during file operations.
	 */
	private static final long serialVersionUID = 1L;
	
	public FixedFileFormatConverterIOException() {
		super();
	}

	public FixedFileFormatConverterIOException(String string, IOException e) {
		super(string, e);
	}
	
	public FixedFileFormatConverterIOException(Exception e) {
		super(e);
	}
	
	public FixedFileFormatConverterIOException(String message) {
		super(message);
	}
}
