package com.octotechnology.fffc.exception;

public class FixedFileFormatCoverterException extends FixedFileFormatException{
	
	private static final long serialVersionUID = 1L;

	public FixedFileFormatCoverterException(String message) {
		super(message);
	}
	
	public FixedFileFormatCoverterException(Throwable e,String message) {
		super(message,e);
	}

}
