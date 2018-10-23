package com.octotechnology.fffc.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.octotechnology.fffc.exception.FixedFileFormatCoverterException;
import com.octotechnology.fffc.exception.FixedFileFormatException;
import com.octotechnology.fffc.exception.FixedFileFormatParserException;
import com.octotechnology.fffc.parser.metadata.ColumnData;

public class CSVWriter {
	List<ColumnData> columnData;
	String outPutFilePath;
	FileWriter fileWriter;
	BufferedWriter bufferedWriter;

	public CSVWriter(List<ColumnData> columnData, String outPutFilePath) {
		this.outPutFilePath = outPutFilePath;
		this.columnData = columnData;
	}

	public void writeHeader() throws FixedFileFormatCoverterException {
		writeLine(columnData.stream().map(ColumnData::getName).collect(Collectors.toList()));
	}
	
	public void init() throws FixedFileFormatException {
		try {
			fileWriter = new FileWriter(new File(outPutFilePath), true);
			bufferedWriter = new BufferedWriter(fileWriter);
		} catch (IOException e) {
			throw new FixedFileFormatParserException("Cannot open output file");
		}
	}

	public void writeLine(List<String> elements) throws FixedFileFormatCoverterException {
		try {
			bufferedWriter.write(String.join(",", elements));
			bufferedWriter.newLine();
		} catch (IOException e) {
			throw new FixedFileFormatCoverterException("Error writing to output file");
		}
	}

	public void close() {
		try {
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bufferedWriter = null;
			fileWriter = null;
		}
	}
}
