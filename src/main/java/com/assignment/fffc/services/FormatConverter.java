package com.assignment.fffc.services;

import com.assignment.fffc.FffcApplication;
import com.assignment.fffc.formats.HeaderFormatProvider;
import com.assignment.fffc.model.Column;
import com.assignment.fffc.processors.DataProcessor;
import com.assignment.fffc.processors.MetaDataProcessor;
import com.assignment.fffc.validators.Validator;
import com.pivovarit.function.ThrowingConsumer;
import com.pivovarit.function.ThrowingFunction;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class FormatConverter {


    private MetaDataProcessor metadataProcessor;

    private DataProcessor dataProcessor;

    private HeaderFormatProvider headerFormatProvider;

    @Autowired
    public FormatConverter(@Qualifier("csvDataProcessor") DataProcessor csvDataProcessor,
                           @Qualifier("csvMetadataProcessor") MetaDataProcessor csvMetadataProcessor,
                           HeaderFormatProvider headerFormatProvider) {
        this.dataProcessor = csvDataProcessor;
        this.metadataProcessor = csvMetadataProcessor;
        this.headerFormatProvider = headerFormatProvider;
    }

    public File convert(String metaDataFilePath, String dataFilePath, String outputFileName, String formatType) throws Exception {

        if (Validator.isValidString(metaDataFilePath, dataFilePath, outputFileName, formatType)) {
            File output = new File(outputFileName);
            File input = new File(dataFilePath);
            try (BufferedWriter buffer = new BufferedWriter(new FileWriter(output), FffcApplication.writerBufferSize);
                 Stream<String> stream = Files.lines(input.toPath());) {
                List<Column> columns = metadataProcessor.extractMetaData(metaDataFilePath);
                addHeader(formatType, buffer, columns);
                stream.iterator().forEachRemaining(ThrowingConsumer.unchecked(
                        line -> {
                            buffer.write(dataProcessor.process(line, columns));
                        }
                        )
                );
            }
            return output;
        } else {
            throw new IllegalArgumentException("metaDatafilePath/dataFilePath/outputFileName/formatType cannot be empty/null");
        }
    }

    private void addHeader(String formatType, BufferedWriter buffer, List<Column> columns) throws IOException {

        String header = headerFormatProvider.addHeader(formatType, columns);
        if (Validator.isValidString(header)) {
            buffer.write(header);
        } else {
            log.info("Header Row Not Supported by format :" + formatType);
        }
    }

}
