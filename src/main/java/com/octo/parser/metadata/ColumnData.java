package com.octo.parser.metadata;

import com.octo.exception.FixedFileFormatCoverterException;

import java.util.Objects;

public class ColumnData {

    private String name;
    private int length;
    private ColumnType columnType;

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
