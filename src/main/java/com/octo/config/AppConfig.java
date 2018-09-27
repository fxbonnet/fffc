package com.octo.config;

import com.octo.coverter.FileFormat;
import com.octo.parser.metadata.ColumnData;

import java.util.List;

/**
 * This class wraps all input parameters required for the file converter
 */
public final class AppConfig {

    private String inputFileName;
    private String outputFileName;
    private FileFormat outputFileFormat;
    private List<ColumnData> columnData;

    /**
     * @param inputFileName    absolute path to the source file
     * @param outputFileName   absolute path to the target file. Target file will be created if it doesnot exist.
     * @param outputFileFormat {@link FileFormat}
     * @param columnData       List of {@link ColumnData} parsed from the metadata file
     */
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
