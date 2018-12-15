package com.octoassessment.processor;

import com.octoassessment.exception.FixedFileFormatConversionException;
import com.octoassessment.model.Metadata;

public interface FileWriter<T,M> {
     void write(T input, String destinationPath, M metadata) throws FixedFileFormatConversionException;
     void closeResources() throws FixedFileFormatConversionException;
     void initialize(String destinationPath, M metadata) throws FixedFileFormatConversionException;
}
