package com.octotechnology.fffc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.octotechnology.fffc.exception.FixedFileFormatParserException;
import com.octotechnology.fffc.parser.MetaDataParser;
import com.octotechnology.fffc.parser.metadata.ColumnData;
import com.octotechnology.fffc.parser.metadata.ColumnType;

public class MetaDataParserTest {
	
	MetaDataParser metaDataParser = new MetaDataParser();

	@Test
	public void testMetaDataFileNotFound() {
		try {
			metaDataParser.parse("nofile.txt");
		} catch (FixedFileFormatParserException e) {
			assertTrue(true);
		}
	}
	
	@Test
    public void testParsedData() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/metadata-valid.txt";
        List<ColumnData> columnDataList =  null;
		try {
			columnDataList = metaDataParser.parse(filePath);
		} catch (FixedFileFormatParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(columnDataList);
        assertEquals(4, columnDataList.size());
        
        assertEquals(ColumnType.DATE, columnDataList.get(0).getColumnType());
        assertEquals(10, columnDataList.get(0).getLength());
        assertEquals("Birth date", columnDataList.get(0).getName());
        
        assertEquals(ColumnType.STRING, columnDataList.get(1).getColumnType());
        assertEquals(15, columnDataList.get(1).getLength());
        assertEquals("First name", columnDataList.get(1).getName());
        
        assertEquals(ColumnType.STRING, columnDataList.get(2).getColumnType());
        assertEquals(15, columnDataList.get(2).getLength());
        assertEquals("Last name", columnDataList.get(2).getName());

        assertEquals(ColumnType.NUMERIC, columnDataList.get(3).getColumnType());
        assertEquals(5, columnDataList.get(3).getLength());
        assertEquals("Weight", columnDataList.get(3).getName());
    }
	
}
