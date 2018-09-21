package org.octo.processor;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.ObjectRowProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;

import org.octo.converter.DateConverter;
import org.octo.mapper.MetadataComponents;
import org.octo.util.ParserConfig;
import org.octo.util.WriterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileProcessor {

    private MetadataComponents metadataComponents;
    private FixedWidthParserSettings parserSettings;
    private CsvWriterSettings csvWriterSettings;
    private static Logger logger = LoggerFactory.getLogger(FileProcessor.class);

    public FileProcessor(String metadataFile) throws Exception{
        init(metadataFile);
    }

    private void init(String metadataFile) throws Exception{
        logger.debug("init method called from FileProcessor");
        try {
            // Configuring csvWriterSettings
            csvWriterSettings = WriterConfig.getCsvWriterSettings();

            // Parsing  meta-data file and printing index of date fields.
            metadataComponents = MetadataComponents.getMetadata(metadataFile);

            // Configuring fixed width File parsing settings.
            parserSettings = ParserConfig.getParserSettings(metadataComponents);
            logger.debug("Initialization complete");
        } catch (Exception e) {
            logger.error("Exception occurred while initializing configuration :" + e.getMessage());
            throw e;
        }
    }

    //Converting Fixed Width File to CSV file
    public void parseFixedWidthFile(String inputFile, String outputFile) throws Exception {
        logger.debug("Processing fixed width file. Input file :" + inputFile +"\t outputFile :" + outputFile);

        FixedWidthParser parser;

        try (
                BufferedReader in = new BufferedReader(new FileReader(new File(inputFile)));
                BufferedWriter output = new BufferedWriter(new FileWriter(outputFile))
             ) {
            if(in.ready()) {
                logger.debug("Ready to read input file");
                CsvWriter csvWriter = new CsvWriter(output, csvWriterSettings);
                csvWriter.writeHeaders(metadataComponents.getFieldNames());

                ObjectRowProcessor rowProcessor = new ObjectRowProcessor() {
                    @Override
                    public void rowProcessed(Object[] row, ParsingContext context)  {
                        if (null != metadataComponents.getDateTypeIndex() && metadataComponents.getDateTypeIndex().length > 0) {
                            try {
                                csvWriter.writeRow(DateConverter.convertDataTypes(row, metadataComponents.getDateTypeIndex()));
                            } catch (Exception e) {
                                logger.warn("Exception occurred while parsing row:" + e.getMessage());
                            }
                        } else {
                            csvWriter.writeRow(row);
                        }
                    }
                };
                parserSettings.setProcessor(rowProcessor);
                parserSettings.setHeaderExtractionEnabled(true);


                // Initiating parser with parserSettings
                parser = new FixedWidthParser(parserSettings);
                parser.beginParsing(in);
                logger.debug("---- File Process Started ----");
                String[] row;
                while ((row = parser.parseNext()) != null);
                logger.debug("---- File Process Ended ----");
                parser.stopParsing();

            } else {
                logger.error("Issue while reading input file");
                throw new Exception("Issue while reading input file");
            }
        } catch (Exception e) {
            logger.error("Exception occurred while parsing  :" + e.getMessage());
            throw e;
        }
    }
}
