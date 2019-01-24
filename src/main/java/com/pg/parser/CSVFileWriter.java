package com.pg.parser;

import java.io.IOException;
import java.util.List;

/**
 * Interface to write the data in the csv file
 */
public interface CSVFileWriter {

    /**
     * method to write the row based on the metadata columns
     * @param columns list of columns  {@link Column}  from the metadata file
     * @param row fixed length row from teh data file
     * @throws IOException exception if cannot write to file
     */
    public void writeRow(List<Column> columns, String row) throws IOException;

    /**
     * method to write the column names to the output file
     * @param columns list of columns from metdata file
     * @throws IOException throws ioexception
     */
    public void writeColumnNames(List<Column> columns) throws IOException;
}
