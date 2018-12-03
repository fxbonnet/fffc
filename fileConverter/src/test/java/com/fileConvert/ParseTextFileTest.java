package com.fileConvert;

import org.junit.Test;

import com.exception.CustomException;
import com.model.MetaData;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class ParseTextFileTest {

	TextFileReader t = new TextFileReader();

	@Test
	public void testParseTextFile() {
		String nextLine = "1970-11-01John           Smith           81.5";

		List<MetaData> metadataValues = new ArrayList<MetaData>();
		metadataValues.add(new MetaData("Birth date", 10, "date"));
		metadataValues.add(new MetaData("First name", 15, "string"));
		metadataValues.add(new MetaData("Last Name", 15, "string"));
		metadataValues.add(new MetaData("Weight", 5, "numeric"));
		String s = null;
		;
		try {
			s = t.parseTextFile(nextLine, metadataValues);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("01/11/1970,John,Smith,81.5", s);
	}

	@Test
	public void testParseTextFile1() {
		String nextLine = "1970-11-01John,           Smith           81.5";

		List<MetaData> metadataValues = new ArrayList<MetaData>();
		metadataValues.add(new MetaData("Birth date", 10, "date"));
		metadataValues.add(new MetaData("First name", 15, "string"));
		metadataValues.add(new MetaData("Last Name", 15, "string"));
		metadataValues.add(new MetaData("Weight", 5, "numeric"));
		String s = null;
		;
		try {
			s = t.parseTextFile(nextLine, metadataValues);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("01/11/1970,\"John,\",Smith,81.0", s);
	}

}