package com.octoassessment.processor;

import com.octoassessment.exception.FixedFileFormatConversionException;
import java.io.IOException;

public interface FileReader<T> {

    boolean hasMoreToRead() throws FixedFileFormatConversionException;
    void setSourcePath(String sourcePath);
    T read() throws IOException, FixedFileFormatConversionException;
    void initialize() throws FixedFileFormatConversionException;
    void closeResouces() throws FixedFileFormatConversionException;
}

