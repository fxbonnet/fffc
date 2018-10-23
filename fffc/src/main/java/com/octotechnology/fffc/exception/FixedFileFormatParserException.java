package com.octotechnology.fffc.exception;

public class FixedFileFormatParserException extends FixedFileFormatException{

	public FixedFileFormatParserException(String message) {
		super(message);
	}
	
	public FixedFileFormatParserException(Throwable e, String message) {
		super(message,e);
	}
	
}
