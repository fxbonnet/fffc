package com.mrdaydreamer.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mrdaydreamer.model.DataRecord;
import com.mrdaydreamer.model.MetaInfo;

import junit.framework.Assert;

public class DataParserTest {
	
	@Test
	public void TestParse() {
		MetaInfo meta1 = mock(MetaInfo.class);
		when(meta1.getColumnName()).thenReturn("Test Date");
		when(meta1.getColumnWidth()).thenReturn(10);
		when(meta1.getDataType()).thenReturn("date");
		
		MetaInfo meta2 = mock(MetaInfo.class);
		when(meta2.getColumnName()).thenReturn("Test Name");
		when(meta2.getColumnWidth()).thenReturn(30);
		when(meta2.getDataType()).thenReturn("string");
		
		MetaInfo meta3 = mock(MetaInfo.class);
		when(meta3.getColumnName()).thenReturn("Test Weight");
		when(meta3.getColumnWidth()).thenReturn(5);
		when(meta3.getDataType()).thenReturn("numeric");
		
		List<MetaInfo> metaList = new ArrayList<>();
		metaList.add(meta1);
		metaList.add(meta2);
		metaList.add(meta3);
		
		try {
		
			String input = "1970-01-01John Smith                     81.5";
			String expected = "01/01/1970,John Smith,81.5";
			DataParser parser = new DataParser(metaList);
			DataRecord output = parser.parse(input);
			Assert.assertEquals(expected, output.print());
			
		} catch (ParsingException e) {
			Assert.fail("Failure in test caused by exception: " + e.getMessage());
		}
		
	}
}
