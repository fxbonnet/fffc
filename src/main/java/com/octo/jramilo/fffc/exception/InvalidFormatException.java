package com.octo.jramilo.fffc.exception;

public class InvalidFormatException extends Exception {

	private static final long serialVersionUID = -2666123921750184996L;

	public InvalidFormatException(Throwable e) {
		super(e);
	}

	public InvalidFormatException(String message) {
		super(message);
	}
	
}
