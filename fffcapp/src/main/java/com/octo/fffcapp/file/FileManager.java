package com.octo.fffcapp.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.octo.fffcapp.exception.FixedFileFormatConverterIOException;

public class FileManager {
	private static volatile FileManager instance = null;
	
	private FileManager() {}
	
	public static FileManager getInstance() {
		if (instance == null) {
            synchronized(FileManager.class) {
                if (instance == null) {
                    instance = new FileManager();
                }
            }
        }

        return instance;
	}
	
	private Path inputFilePath;
	private Path metadataFilePath;
	private Path outputFilePath;

	public Path getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = Paths.get(inputFilePath);
	}

	public Path getMetadataFilePath() {
		return metadataFilePath;
	}

	public void setMetadataFilePath(String metadataFilePath) {
		this.metadataFilePath = Paths.get(metadataFilePath);
	}

	public Path getOutputFilePath() {
		return outputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = Paths.get(outputFilePath);
	}
	
	public boolean checkFileLocations() throws FixedFileFormatConverterIOException{
		if(inputFilePath == null) throw new FixedFileFormatConverterIOException("Input file path is not set.");
		
		try {
			inputFilePath.toRealPath();
		} catch (IOException e) {
			throw new FixedFileFormatConverterIOException("Input file path [" + inputFilePath + "] is not correct.", e);
		}
		
		if(metadataFilePath == null) throw new FixedFileFormatConverterIOException("Metadata file path is not set.");
		
		try {
			metadataFilePath.toRealPath();
		} catch (IOException e) {
			throw new FixedFileFormatConverterIOException("Metadata file path [" + metadataFilePath + "] is not correct.", e);
		}
		
		if(outputFilePath == null) throw new FixedFileFormatConverterIOException("Output file path is not set.");
		
		return true;
	}
}
