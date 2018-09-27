package com.octo;

import com.octo.coverter.FileConverter;
import com.octo.coverter.FileConverterFactory;
import com.octo.exception.FixedFileFormatCoverterException;
import com.octo.parser.FileMetaDataParser;
import com.octo.config.AppConfig;
import com.octo.config.ArgumentParser;
import com.octo.parser.metadata.ColumnData;

import java.util.List;

/**
 * Fixed file format converter Application
 */
public class App {

    private ArgumentParser argumentParser;
    private FileMetaDataParser fileMetaDataParser;
    private FileConverterFactory fileConverterFactory;

    public App(ArgumentParser argumentParser,
               FileMetaDataParser fileMetaDataParser,
               FileConverterFactory fileConverterFactory) {
        this.argumentParser = argumentParser;
        this.fileMetaDataParser = fileMetaDataParser;
        this.fileConverterFactory = fileConverterFactory;
    }

    /**
     * Initiate the file conversion process
     *
     * @param args
     */
    public void start(String[] args) {
        if (!argumentParser.parseArgs(args)) {
            return;
        }

        List<ColumnData> columnDataList = fileMetaDataParser.parse(argumentParser.getInputFormatFileName());

        AppConfig appConfig = new AppConfig(argumentParser.getInputFileName(), argumentParser.getOutputFileName(),
                argumentParser.getOutputFileFormat(), columnDataList);

        FileConverter fileConverter = fileConverterFactory.getConverter(appConfig);
        fileConverter.convert();


    }

    public static void main(String[] args) {

        App app = new App(new ArgumentParser(), new FileMetaDataParser(),
                FileConverterFactory.instance());

        try {
            app.start(args);
        } catch (FixedFileFormatCoverterException e) {
            System.out.println("Error processing: \n\t Details: " + e.getMessage());
        }


    }

}
