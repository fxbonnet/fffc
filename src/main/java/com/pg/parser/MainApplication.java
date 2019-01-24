package com.pg.parser;

import com.google.common.collect.Iterators;
import com.pg.parser.impl.CSVFileReaderImpl;
import com.pg.parser.impl.CSVFileWriterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Client Application
 */
public class MainApplication {

    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

    /**
     * the size of rows to be read
     */
    private static int cacheSize = 5000000;

    public static void main(String[] args) {
        MainApplication app = new MainApplication();

        try {
            String dataFileName = "data.dat";
            String metaDataFileName = "metadata.csv";
            app.processDataFile(dataFileName, metaDataFileName);
        } catch (IOException e) {
            logger.error("IO error {}", e.getLocalizedMessage());
        }
    }

    /**
     * Method to process the input file by reading using third party library
     *
     * @param dataFileName    input source data file name
     * @param metdataFileName metdata file name
     * @throws Exception throws IOException
     */
    private void processDataFile(String dataFileName, String metdataFileName) throws IOException {
        CSVFileReader csvFileReader = new CSVFileReaderImpl(metdataFileName, ",");
        Writer writer = new FileWriter(dataFileName + ".csv");
        CSVFileWriter csvFileWriter = new CSVFileWriterImpl(writer, ",", "\n");
        csvFileWriter.writeColumnNames(csvFileReader.getColumns());
        int rowLength = csvFileReader.getColumns().stream().mapToInt(Column::getLength).sum();
        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(dataFileName).toURI()))) {
            Iterator<List<String>> iterator = Iterators.partition(stream.iterator(), cacheSize);
            while (iterator.hasNext()) {
                iterator.next().stream().forEach(line -> {
                    try {
                        if (line.length() != rowLength) {
                            throw new IOException("rowlength not correct should be " + rowLength + " but was " + line.length());
                        }
                        csvFileWriter.writeRow(csvFileReader.getColumns(), line);
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                        logger.error("Error Writing to the file, line -> {} ", line);
                    }
                });

            }
        } catch (IOException | URISyntaxException e) {
            logger.error("Exception while processing the file {}", e.getLocalizedMessage());
        }
    }
}
