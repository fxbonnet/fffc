package com.fileConvert;

import org.junit.Test;

import com.model.MetaData;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class MetaDataFileTest {

	MetaDataFileReader reader = new MetaDataFileReader();

	@Test
	public void testwriteCSVHeader() {

		List<MetaData> metadataValues = new ArrayList<MetaData>();
		metadataValues.add(new MetaData("Birth date", 10, "date"));
		metadataValues.add(new MetaData("First name", 15, "string"));
		metadataValues.add(new MetaData("Last Name", 15, "string"));
		metadataValues.add(new MetaData("Weight", 5, "numeric"));
		String s = null;
		;

		s = reader.writeCSVHeader(metadataValues);

		assertEquals("Birth date,First name,Last Name,Weight\n", s);
	}
}
