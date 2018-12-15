package com.octoassessment.service;

import com.octoassessment.exception.FixedFileFormatConversionException;
import com.octoassessment.model.ConversionParams;

public interface FixedFileFormatConversionService {

     void process(ConversionParams params) throws FixedFileFormatConversionException;
}
