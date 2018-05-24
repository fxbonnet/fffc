package com.octo.fffc.reader;

import com.octo.fffc.exceptions.ReaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;

/**
 * Reader class responsible to read a given file.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public class FFFCReader implements Reader {

    private static final Logger logger = LoggerFactory.getLogger(FFFCReader.class);
    private final BufferedReader reader;

    public FFFCReader(String file) throws ReaderException {
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (FileNotFoundException ex) {
            String message = format("Unable to create reader to read file[%s]", file);
            logger.error(message, ex);
            throw new ReaderException(message, ex);
        }
    }

    @Override
    public String nextLine() throws ReaderException {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            logger.error("Exception occured while reading next line",ex);
            throw new ReaderException("Exception occured while reading next line",ex);
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}