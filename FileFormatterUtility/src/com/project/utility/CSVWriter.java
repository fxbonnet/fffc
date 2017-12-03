package com.project.utility;

import com.project.utility.exception.FileFormatterException;
import com.project.utility.utils.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * CSV writer that implements the FileFormatWriter
 * Created by rssridh on 12/3/17.
 */
public class CSVWriter implements FileFormatWriter {
    private final String DELIMITER = ",";
    private final String FILE_EXTENSION = "csv";

    private File csvFile;
    private FileWriter fileWriter;
    private String fileDelimiter;

    public CSVWriter(final String filename) throws FileFormatterException {
        setDelimiter();
        try {
            csvFile = new File(StringUtils.converFileExtensions(filename, FILE_EXTENSION));
            fileWriter = new FileWriter(csvFile);
        } catch (IOException e) {
            throw new FileFormatterException("We ran into an unexpected error while converting the "
                    + "input file to output csv file - " + e.getMessage());
        }
    }

    /**
     * Writes a line to the file.
     * @param line
     * @throws FileFormatterException
     */
    public void writeLine(final String line) throws FileFormatterException {
        try {
            fileWriter.append(line);
            fileWriter.write(System.getProperty("line.separator"));
            fileWriter.flush();
        } catch (IOException e) {
            throw new FileFormatterException("We ran into an unexpected error while writing to the "
                    + "output csv file - " + e.getMessage());
        }
    }

    /**
     * Closes the file
     * @throws FileFormatterException
     */
    public void closeFile() throws FileFormatterException {
        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new FileFormatterException("We ran into an unexpected error while closing the "
                    + "output csv file - " + e.getMessage());
        }
    }

    /**
     * Gets the delimiter for this file format.
     * @return
     */
    public String getDelimiter() {
        return this.fileDelimiter;
    }

    /**
     * Sets the delimiter for this file format.
     */
    public void setDelimiter() {
        this.fileDelimiter = DELIMITER;
    }
}
