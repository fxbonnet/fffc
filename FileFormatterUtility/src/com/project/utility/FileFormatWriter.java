package com.project.utility;

import com.project.utility.exception.FileFormatterException;

/**
 * Interface to be implemented for different file format writers.
 * Created by rssridh on 12/4/17.
 */
interface FileFormatWriter {
    void writeLine(final String line) throws FileFormatterException;
    void closeFile() throws FileFormatterException;
    String getDelimiter();
    void setDelimiter();
}
