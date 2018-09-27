package com.octo.coverter.csv;

import com.octo.config.AppConfig;
import com.octo.coverter.SyncFileConverter;
import com.octo.exception.FixedFileFormatCoverterException;
import com.octo.parser.ColumnTypeParserFactory;
import com.octo.parser.metadata.ColumnData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Concrete implementation for converting to CSV files synchronously.
 */
public final class CSVFileConverter implements SyncFileConverter<String> {


    private final ColumnTypeParserFactory columnTypeParserFactory;
    private final AppConfig appConfig;

    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public CSVFileConverter(AppConfig appConfig, ColumnTypeParserFactory columnTypeParserFactory) {
        this.columnTypeParserFactory = columnTypeParserFactory;
        this.appConfig = appConfig;
    }

    @Override
    public void init() {

        try {
            fileWriter = new FileWriter(new File(appConfig.getOutputFileName()), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            writeLine(0, this.appConfig.getColumnData()
                    .stream()
                    .map(ColumnData::getName)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            throw new FixedFileFormatCoverterException("Cannot create/open output file");
        }
    }

    @Override
    public boolean writeLine(long lineNum, List<String> elements) {

        if (Objects.isNull(bufferedWriter)) {
            throw new IllegalStateException("Output file is not open");
        }

        try {
            bufferedWriter.write(String.join(",", elements));
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new FixedFileFormatCoverterException("Error writing to file");
        }

        return true;
    }

    @Override
    public void cleanUp() {
        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new FixedFileFormatCoverterException("Error closing output file");
        } finally {
            bufferedWriter = null;
            fileWriter = null;
        }
    }

    @Override
    public ColumnTypeParserFactory<String> getColumnTypeParserFactory() {
        return this.columnTypeParserFactory;
    }

    @Override
    public AppConfig getAppConfig() {
        return this.appConfig;
    }
}
