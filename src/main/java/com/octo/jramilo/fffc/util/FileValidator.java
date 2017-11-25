package com.octo.jramilo.fffc.util;

import java.io.File;

public class FileValidator {
	public static void validate(File file, boolean isInputFile){
		String message = null;
		
		if(file == null) {
			message = ErrorMessage.FILE_NULL;
		} else if (!file.exists()) {
			message = ErrorMessage.FILE_NOT_EXISTS;
		} else if(isInputFile && !file.canRead()) {
			message = ErrorMessage.FILE_CANNOT_READ;
		} else if(!isInputFile && !file.canWrite()) {
			message = ErrorMessage.FILE_CANNOT_WRITE;
		} else if(file.isDirectory()) {
			message = ErrorMessage.FILE_IS_DIRECTORY;
		}
		
		if(message != null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void validateNonEmpty(File file){
		String message = null;
		
		if(file.length() == 0) {
			message = ErrorMessage.FILE_EMPTY;
		}
		
		if(message != null) {
			throw new IllegalArgumentException(message);
		}
	}
}
