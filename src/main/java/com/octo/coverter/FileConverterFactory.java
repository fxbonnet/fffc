package com.octo.coverter;

import com.octo.coverter.csv.CSVFileConverter;
import com.octo.exception.FixedFileFormatCoverterException;
import com.octo.parser.DefaultColumnTypeParserFactory;
import com.octo.config.AppConfig;

/**
 * A factory for available {@link FileConverter}
 */
public final class FileConverterFactory {


    private static FileConverterFactory instance;


    private FileConverterFactory() {

    }

    /**
     * Provide a singleton instance of {@link FileConverterFactory}
     *
     * @return
     */
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

    /**
     * Returns a {@link FileConverter} for the given {@link FileFormat} property of the {@link AppConfig}
     *
     * @param appConfig
     * @return throw {@link FixedFileFormatCoverterException} if the {@link FileFormat} is not supported.
     */
    public final FileConverter getConverter(AppConfig appConfig) {


        switch (appConfig.getOutputFileFormat()) {
            case CSV:
                return new CSVFileConverter(appConfig, DefaultColumnTypeParserFactory.instance());
            default:
                throw new FixedFileFormatCoverterException("File converter not found for type " + appConfig.getOutputFileFormat());
        }


    }
}
