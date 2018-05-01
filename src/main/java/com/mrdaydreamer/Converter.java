package com.mrdaydreamer;

import java.io.IOException;
import java.util.List;

import com.mrdaydreamer.io.InputReader;
import com.mrdaydreamer.io.MetaReader;
import com.mrdaydreamer.io.OutputWriter;
import com.mrdaydreamer.model.DataRecord;
import com.mrdaydreamer.model.MetaInfo;
import com.mrdaydreamer.util.DataParser;
import com.mrdaydreamer.util.InvalidInputException;

public class Converter {

	public static void main(String[] args) {

		MetaReader metaReader = new MetaReader();
		List<MetaInfo> metaList;
		
		try {
			metaList = metaReader.read("data/meta.txt");
			DataParser parser = new DataParser(metaList);
			InputReader inputReader = new InputReader();
			List<DataRecord> data = inputReader.read("data/input.txt", parser);
			
			OutputWriter outputWriter = new OutputWriter();
			outputWriter.write("data/output.csv", data);
		} catch (IOException | InvalidInputException e) {
			System.out.println("IOException or invalid input data!");
			System.exit(-1);
		}
	}
}
