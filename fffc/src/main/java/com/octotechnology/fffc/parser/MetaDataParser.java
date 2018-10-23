package com.octotechnology.fffc.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.octotechnology.fffc.exception.FixedFileFormatException;
import com.octotechnology.fffc.exception.FixedFileFormatParserException;
import com.octotechnology.fffc.parser.metadata.ColumnData;

/**
 * Parser class to parse meta data file
 */
public class MetaDataParser {
	
	
	public MetaDataParser() {
		
	}
	
    /**
     * Parse the meta data file and store column details in a list
     * @throws FixedFileFormatException 
     */
    public List<ColumnData> parse(String metaDataFileName) throws FixedFileFormatParserException {
    	List<ColumnData> columnDataList = new ArrayList<ColumnData>();
    	Scanner scanner = null;
        File metaDataFile = null;
        try {
        	metaDataFile = new File(metaDataFileName);
             scanner = new Scanner(metaDataFile, StandardCharsets.UTF_8.name());
            while (scanner.hasNext()) {
                String[] params = scanner.nextLine().split(",");
                columnDataList.add(new ColumnData(params));
            }

        } catch (FileNotFoundException e) {
            throw new FixedFileFormatParserException("Exception while parsing metadataFile: " + e.getMessage());
        }
        finally {
        	if(!Objects.isNull(scanner))
        		scanner.close();
        }
        return columnDataList;
    }
}

