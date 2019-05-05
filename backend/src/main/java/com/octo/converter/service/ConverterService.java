package com.octo.converter.service;

import java.io.File;
import java.io.IOException;

public interface ConverterService {
    File convert(File fixedFormatfile, File metadataCsv) throws ConverterException, IOException;
}
