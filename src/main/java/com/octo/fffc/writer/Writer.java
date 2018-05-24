package com.octo.fffc.writer;

import com.octo.fffc.exceptions.WriterException;

import java.io.Closeable;

/**
 * This class defines the interface for writing the data.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Writer extends Closeable {

    /**
     * Write the given data.
     *
     * @param data data to be written.
     * @throws WriterException - Exception thrown under error conditions.
     */
    void write(String[] data) throws WriterException;
}
