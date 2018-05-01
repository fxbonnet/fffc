package com.mrdaydreamer.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mrdaydreamer.model.MetaInfo;
import com.mrdaydreamer.util.InvalidInputException;

public class MetaReader {
	
	public List<MetaInfo> read(String inputFileName) throws IOException, InvalidInputException {
		File inputFile = new File(inputFileName);
		FileReader inputReader = new FileReader(inputFile);
		BufferedReader bufferedReader = new BufferedReader(inputReader);
		
		List<MetaInfo> metaList = new ArrayList<>();
		
		String line, columnName, dataType;
		int columnWidth;
		while((line = bufferedReader.readLine()) != null) {
			String[] tokens = line.split(",");
			columnName = tokens[0];
			columnWidth = Integer.valueOf(tokens[1]);
			dataType = tokens[2];
			metaList.add(new MetaInfo(columnName, columnWidth, dataType));
		}
		
		bufferedReader.close();
		return metaList;
	}
}
