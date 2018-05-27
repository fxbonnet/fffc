package com.octo.fffc.handlers;

import com.octo.fffc.exception.InvalidInputException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implements {@link FixedFileFormatReader} with a {@link BufferedReader}
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public class BufferedFixedFileFormatReader implements FixedFileFormatReader {

    private final BufferedReader reader;

    public BufferedFixedFileFormatReader(String inputFile) throws InvalidInputException {
        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            throw new InvalidInputException("The input file is invalid : " + e.getMessage());
        }
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
