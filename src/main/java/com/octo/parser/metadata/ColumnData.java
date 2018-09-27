package com.octo.parser.metadata;

import com.octo.exception.FixedFileFormatCoverterException;

import java.util.Objects;

/**
 * Holds the metadata of a column in the input file
 */
public class ColumnData {

    /**
     * Name of the column
     */
    private String name;
    /**
     * length of the column in the fixed length file
     */
    private int length;
    /**
     * Type of column data
     */
    private ColumnType columnType;

    /**
     * @param params a line in the metadata file represented as array of string
     */
    public ColumnData(String[] params) {

        if (Objects.isNull(params) || params.length < 3) {
            throw new FixedFileFormatCoverterException("Invalid metadata file format");
        }

        this.name = params[0];
        try {
            this.length = Integer.parseInt(params[1]);
        } catch (NumberFormatException e) {
            new FixedFileFormatCoverterException("Column size in Metadata should be a positivenumber");
        }

        try {
            columnType = ColumnType.valueOf(params[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            new FixedFileFormatCoverterException("Unsupported Column type found in metadata file");
        }

        if (this.length < 0) {
            new FixedFileFormatCoverterException("Column size in Metadata should be a positive number");
        }

    }


    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public ColumnType getColumnType() {
        return columnType;
    }
}
