package com.octo.fffcapp.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.octo.fffcapp.exception.FixedFileFormatConverterIOException;

public class FileWriter implements AutoCloseable{
private BufferedWriter bufferedWriter;
	
	public FileWriter(Path filePath) {
		try {
			bufferedWriter = Files.newBufferedWriter(filePath, Charset.forName("UTF-8"), 
					new OpenOption[] {StandardOpenOption.WRITE, StandardOpenOption.CREATE});
		} catch(IOException e) {
			closeWriter();
			
			throw new FixedFileFormatConverterIOException(e);
		}
	}
	
	public void write(String line) throws FixedFileFormatConverterIOException {
		try {
			bufferedWriter.write(line);
			bufferedWriter.write(System.lineSeparator());
		} catch (IOException e) {
			closeWriter();
			
			throw new FixedFileFormatConverterIOException(e);
		}
	}
	
	public void closeWriter() throws FixedFileFormatConverterIOException {
		if(bufferedWriter != null) {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				throw new FixedFileFormatConverterIOException(e);
			} finally {
				bufferedWriter = null;
			}
		}
	}

	@Override
	public void close() throws FixedFileFormatConverterIOException {
		closeWriter();
	}
}
