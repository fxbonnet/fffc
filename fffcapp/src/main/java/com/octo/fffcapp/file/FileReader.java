package com.octo.fffcapp.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.octo.fffcapp.exception.FixedFileFormatConverterIOException;
import com.octo.fffcapp.exception.FixedFileFormatConverterParseException;
import com.octo.fffcapp.parser.Parser;

public class FileReader {
	//private static Logger logger = LogManager.getLogger(FileReader.class);
	
	private Path filePath;
	
	public FileReader(Path filePath) {
		this.filePath = filePath;
	}
	
	public void read(Parser<Stream<String>> parser) throws FixedFileFormatConverterIOException, FixedFileFormatConverterParseException {
		try (Stream<String> stream = Files.lines(filePath, Charset.forName("UTF-8"))) {
			parser.accept(stream);
		} catch (FixedFileFormatConverterParseException e) {
			throw e;
		} catch (IOException e) {
			throw new FixedFileFormatConverterIOException(e);
		}
	}
}
