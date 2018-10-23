package com.octotechnology.fffc.exception;

public class FixedFileFormatException extends Exception{
	
	public FixedFileFormatException(String message) {
		super(message);
	}
	
	public FixedFileFormatException(String message, Throwable e) {
		super(message,e);
	}

}
