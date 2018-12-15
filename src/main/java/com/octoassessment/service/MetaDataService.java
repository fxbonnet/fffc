package com.octoassessment.service;

import com.octoassessment.exception.FixedFileFormatConversionException;

public interface MetaDataService<T> {

    T readMetadata(String path) throws FixedFileFormatConversionException;
}
