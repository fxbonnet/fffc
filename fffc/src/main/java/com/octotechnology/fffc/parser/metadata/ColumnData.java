package com.octotechnology.fffc.parser.metadata;

import java.util.Objects;

import com.octotechnology.fffc.exception.FixedFileFormatParserException;

/**
 * Column meta data bean
 */
public class ColumnData {

    /**
     * Column name
     */
    private String name;
    /**
     * Column length
     */
    private int length;
    /**
     * Column type
     */
    private ColumnType columnType;

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public ColumnType getColumnType() {
        return columnType;
    }
    
    
    /**
     * @param columnDataValues - String array of column details from meta data file
     * @throws FixedFileFormatParserException 
     */
    public ColumnData(String[] columnDetails) throws FixedFileFormatParserException {
        if (Objects.isNull(columnDetails) || columnDetails.length < 3) {
            throw new FixedFileFormatParserException("Invalid or empty metadata file");
        }
        this.name = columnDetails[0];
        if(this.length < 0) {
        	throw new FixedFileFormatParserException("Column size '"+columnDetails[1]+"' should be a positive number");
        }
        try {
            this.length = Integer.parseInt(columnDetails[1]);
        } catch (NumberFormatException e) {
            throw new FixedFileFormatParserException("Column size '"+columnDetails[1]+"' should be a positive number");
        }
        try {
            columnType = ColumnType.valueOf(columnDetails[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new FixedFileFormatParserException("Unsupported Column type : "+columnDetails[2]);
        }
    }
}

