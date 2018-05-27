package com.octo.fffc.handlers;

import com.octo.fffc.exception.InvalidInputException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.System.lineSeparator;
import static org.hibernate.validator.internal.util.StringHelper.join;

/**
 * Implements {@link CsvWriter} to provide a {@link BufferedWriter}
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public class BufferedCsvWriter implements CsvWriter {

    private final BufferedWriter writer;
    private final String delimiter;
    private final String lineDelimiter;

    public BufferedCsvWriter(String outputFile, String delimiter) throws InvalidInputException {
        this(outputFile, delimiter, lineSeparator());
    }

    public BufferedCsvWriter(String outputFile, String delimiter, String lineDelimiter) throws InvalidInputException {
        try {
            this.delimiter = delimiter;
            this.lineDelimiter = lineDelimiter;
            writer = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            throw new InvalidInputException("Output file is invalid", e);
        }
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    @Override
    public void write(String[] input) throws IOException {
        writer.write(join(input, delimiter) + lineDelimiter);
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}
