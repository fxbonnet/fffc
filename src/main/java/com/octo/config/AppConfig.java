package com.octo.config;

import com.octo.coverter.FileFormat;
import com.octo.parser.metadata.ColumnData;

import java.util.List;

public final class AppConfig {

    private String inputFileName;
    private String outputFileName;
    private FileFormat outputFileFormat;
    private List<ColumnData> columnData;

    public AppConfig(String inputFileName, String outputFileName, FileFormat outputFileFormat, List<ColumnData> columnData) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.outputFileFormat = outputFileFormat;
        this.columnData = columnData;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public FileFormat getOutputFileFormat() {
        return outputFileFormat;
    }

    public List<ColumnData> getColumnData() {
        return columnData;
    }
}
