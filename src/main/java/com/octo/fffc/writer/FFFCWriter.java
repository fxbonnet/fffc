package com.octo.fffc.writer;

import au.com.bytecode.opencsv.CSVWriter;
import com.octo.fffc.exceptions.WriterException;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import static org.slf4j.LoggerFactory.getLogger;

public class FFFCWriter implements Writer {

    private static final Logger logger = getLogger(FFFCWriter.class);
    private final CSVWriter writer;

    public FFFCWriter(String filePath) throws WriterException {
        try {
            writer = new CSVWriter(
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)),
                    ',',
                    '"',
                    "\r\n");
        } catch (FileNotFoundException ex) {
            logger.error("Cannot open file for writing ",ex);
            throw new WriterException("Cannot open file for writing ", ex);
        }
    }

    @Override
    public void write(String[] data) throws WriterException {
        try {
            writer.writeNext(data);
        } catch (Exception ex) {
            logger.error("Cannot write record ");
            throw new WriterException("Unable to write record", ex);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
