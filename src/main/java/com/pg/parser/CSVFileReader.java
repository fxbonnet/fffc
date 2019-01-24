package com.pg.parser;

import java.util.List;

/**
 * Interface to read the csv file metadata
 */
public interface CSVFileReader {

    /**
     * Method to get the columns from the metadata file
     * @return list of {@link Column}
     */
    List<Column> getColumns();
}
