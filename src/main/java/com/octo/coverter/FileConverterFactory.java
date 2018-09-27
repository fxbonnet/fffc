package com.octo.coverter;

import com.octo.coverter.csv.CSVFileConverter;
import com.octo.exception.FixedFileFormatCoverterException;
import com.octo.parser.DefaultColumnTypeParserFactory;
import com.octo.config.AppConfig;

public final class FileConverterFactory {


    private static FileConverterFactory instance;


    private FileConverterFactory() {

    }

    public static FileConverterFactory instance() {

        if (instance == null) {
            synchronized (FileConverterFactory.class) {
                if (instance == null) {
                    instance = new FileConverterFactory();
                }
            }
        }

        return instance;

    }

    public final FileConverter getConverter(AppConfig appConfig) {


        switch (appConfig.getOutputFileFormat()) {
            case CSV:
                return new CSVFileConverter(appConfig, DefaultColumnTypeParserFactory.instance());
            default:
                throw new FixedFileFormatCoverterException("File converter not found for type " + appConfig.getOutputFileFormat());
        }


    }
}
