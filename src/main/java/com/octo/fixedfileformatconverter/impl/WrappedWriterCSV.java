/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter.impl;

import static com.octo.fixedfileformatconverter.FileConverter.CHAR_ESCAPE;
import static com.octo.fixedfileformatconverter.FileConverter.CHAR_NEWLINE;
import static com.octo.fixedfileformatconverter.FileConverter.CHAR_QUOTE;
import static com.octo.fixedfileformatconverter.FileConverter.CHAR_SEPARATOR;
import com.octo.fixedfileformatconverter.WrappedWriter;
import com.opencsv.CSVWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapped Writer CSV.
 *
 * The {@link WrappedWriter} implementation, which writes out a CSV file. If the specified output file exists, it will
 * be truncated when written to.
 *
 * @author Mark Zsilavecz
 *
 * @param <T> the type that the write expects lines in.
 */
public class WrappedWriterCSV<T> implements WrappedWriter<String[]>
{

    /**
     * Logging instance.
     */
    public static final Logger LOG = LoggerFactory.getLogger(WrappedWriterCSV.class);

    //private final Path output;
    private final BufferedWriter writer;
    private final CSVWriter csvWriter;

    public WrappedWriterCSV(Path output) throws IOException
    {
        //this.output = output;
        this.writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8,
                                              StandardOpenOption.WRITE,
                                              StandardOpenOption.CREATE,
                                              StandardOpenOption.TRUNCATE_EXISTING);
        this.csvWriter = new CSVWriter(writer, CHAR_SEPARATOR, CHAR_QUOTE, CHAR_ESCAPE, CHAR_NEWLINE);
        LOG.info("WrappedWriterCSV created, will write to {}.", output);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void write(String[] line) throws IOException
    {
        if (writer == null || csvWriter == null)
        {
            throw new IOException("Writers are null.");
        }
        csvWriter.writeNext(line, false);
        csvWriter.flush();
    }

    @Override
    public void close() throws IOException
    {
        if (csvWriter != null)
        {
            csvWriter.close();
        }
        if (writer != null)
        {
            writer.close();
        }
    }

}
