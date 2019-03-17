package com.octo.code.practice.exception;

public class CSVConverterCustomizedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CSVConverterCustomizedException(String errorMessage) {
		super(errorMessage);
	}
}
