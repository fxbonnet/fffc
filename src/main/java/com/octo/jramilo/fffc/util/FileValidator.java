package com.octo.jramilo.fffc.util;

import java.io.File;

import com.octo.jramilo.fffc.exception.InvalidFileExpection;

public class FileValidator {
	public static void validate(File file, boolean isInputFile) throws InvalidFileExpection {
		String message = null;
		
		if(file == null) {
			message = "The file could not be null!";
		} else if (!file.exists()) {
			message = "The file does not exists!";
		} else if(isInputFile && !file.canRead()) {
			message = "The file is unreadable!";
		} else if(!isInputFile && !file.canWrite()) {
			message = "Cannot write to file!";
		} else if(file.isDirectory()) {
			message = "The file could not be a directory!";
		}
		
		if(message != null) {
			throw new InvalidFileExpection(message);
		}
	}
}
