package com.assignment.fffc.services;

import com.assignment.fffc.processors.DataProcessor;
import com.assignment.fffc.processors.MetaDataProcessor;
import com.pivovarit.function.ThrowingConsumer;
import com.pivovarit.function.ThrowingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

@Service
public class FormatConverter {


    private MetaDataProcessor metadataProcessor;

    private DataProcessor dataProcessor;

    @Autowired

    public FormatConverter(@Qualifier("csvDataProcessor") DataProcessor csvDataProcessor, @Qualifier("csvMetadataProcessor") MetaDataProcessor csvMetadataProcessor) {
        this.dataProcessor = csvDataProcessor;
        this.metadataProcessor = csvMetadataProcessor;
    }

    public File convert(String metaDataFilePath, String dataFilePath, String outputFileName) throws Exception {

        File output = new File(outputFileName);
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(output))) {
            Files.lines(new File(dataFilePath).toPath()).map(ThrowingFunction.unchecked(line ->
                    dataProcessor.process(line, metadataProcessor.extractMetaData(metaDataFilePath)) + "\n"
            )).forEach(ThrowingConsumer.unchecked(buffer::write));
        }
        return output;
    }

}
