package com.fileConvert;

import org.junit.Ignore;

import com.exception.CustomException;

public class FileParserTest {
	FileParser file = new FileParser();

	@Ignore
	public void testwriteCSVHeader() {

		try {
			file.readWriteFile("/textinput.txt", "/outputcsv.csv",
					"/metadata.csv");
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
