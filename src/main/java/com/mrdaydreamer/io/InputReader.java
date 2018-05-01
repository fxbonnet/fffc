package com.mrdaydreamer.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mrdaydreamer.model.DataRecord;
import com.mrdaydreamer.util.DataParser;
import com.mrdaydreamer.util.InvalidInputException;
import com.mrdaydreamer.util.ParsingException;

public class InputReader {
	
	private final String EMPTY = "";
	
	public List<DataRecord> read(String inputFileName, DataParser parser) throws IOException, InvalidInputException {
		
		File inputFile = new File(inputFileName);
		FileReader inputReader = new FileReader(inputFile);
		BufferedReader bufferedReader = new BufferedReader(inputReader);

		List<DataRecord> dataList = new ArrayList<>();

		String line = EMPTY;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				dataList.add(parser.parse(line));
			}
		} catch (ParsingException e) {
			throw new InvalidInputException("Invalid data line: [" + line + "]", e);
		} finally {
			bufferedReader.close();
		}
		return dataList;
	}
}
