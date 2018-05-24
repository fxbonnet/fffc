package com.octo.fffc.reader;

import com.octo.fffc.exceptions.ReaderException;

import java.io.Closeable;

/**
 * This class defines the interface contract for reading the files
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Reader extends Closeable {

    /**
     * Returns the nextLine read.
     *
     * @return the next line read.
     */
    String nextLine() throws ReaderException;
}
