package com.assignment.fffc.services;

import com.assignment.fffc.formats.HeaderFormatProvider;
import com.assignment.fffc.model.Column;
import com.assignment.fffc.processors.DataProcessor;
import com.assignment.fffc.processors.MetaDataProcessor;
import com.pivovarit.function.ThrowingConsumer;
import com.pivovarit.function.ThrowingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;

@Service
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

        File output = new File(outputFileName);
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(output))) {

            List<Column> columns = metadataProcessor.extractMetaData(metaDataFilePath);

            // Add Header
            String header = headerFormatProvider.addHeader(formatType, columns);
            if (!StringUtils.isEmpty(header)) {
                buffer.write(header);
            }
            
            // Add Body
            Files.lines(new File(dataFilePath).toPath()).map(ThrowingFunction.unchecked(line -> {
                        return dataProcessor.process(line, columns);
                    }
            )).forEach(ThrowingConsumer.unchecked(buffer::write));

            // Add Footer Can be implemented in the future

        }
        return output;
    }

}
