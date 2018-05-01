package com.mrdaydreamer.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.mrdaydreamer.model.DataRecord;

public class OutputWriter {

	public void write(String outputFileName, List<DataRecord> dataList) throws IOException {
		File outputFile = new File(outputFileName);
		FileWriter outputWriter = new FileWriter(outputFile);
		BufferedWriter bufferedWriter = new BufferedWriter(outputWriter);
		
		for(int i = 0; i < dataList.size(); i++) {
			bufferedWriter.write(dataList.get(i).print());
			if(i < dataList.size() - 1) {
				bufferedWriter.newLine();
			}
		}
		bufferedWriter.close();
	}
}
