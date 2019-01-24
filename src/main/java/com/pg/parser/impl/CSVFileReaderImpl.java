package com.pg.parser.impl;

import com.pg.parser.CSVFileReader;
import com.pg.parser.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Implementation class for {@link CSVFileReader} interface
 * Implementation to read the csv file metadata
 */
public class CSVFileReaderImpl implements CSVFileReader {

    private static final Logger logger = LoggerFactory.getLogger(CSVFileReaderImpl.class);

    /**
     * list of cloumn {@link Column} from the metdata
     */
    private final List<Column> columns = new ArrayList<>();

    public CSVFileReaderImpl(String metaDataFileName, String delimeter) {
        readMetaData(metaDataFileName, delimeter);
    }

    @Override
    public List<Column> getColumns() {
        return columns;
    }

    private void readMetaData(String fileName, String delimeter) {

        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {
            stream.forEach(e -> {
                String[] metaDataRow = e.split(delimeter);
                columns.add(new Column(metaDataRow[0], Integer.parseInt(metaDataRow[1]), metaDataRow[2]));
            });
        } catch (Exception e) {
            logger.error("Error Reading the metadata file {}", e.getMessage());
        }
    }

}


