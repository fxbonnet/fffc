/*
 * Copyright 2018 OCTO Technology.
 */
package com.octo.fixedfileformatconverter;

import java.io.IOException;
import java.io.Writer;

/**
 * Writer.
 *
 * A writer can write lines of output, of a certain type, to a file using a standard Java {@link Writer}.
 *
 * @author Mark Zsilavecz
 *
 * @param <T> the type of the line to write.
 */
public interface WrappedWriter<T> extends AutoCloseable
{

    /**
     * Write a line.
     *
     * @param line the line to write out.
     *
     * @throws IOException if an error occurs while writing the line.
     */
    public void write(T line) throws IOException;

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() throws IOException;

}
