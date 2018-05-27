package com.octo.fffc.handlers;

import java.io.IOException;

/**
 * Provides a contract for reading the file.
 * <p>
 * Note that the contract gives us the ability to implement and experiment with different
 * File Format readers
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public interface FixedFileFormatReader extends AutoCloseable {

    /**
     * Reads a single line from the given file
     *
     * @return
     * @throws IOException
     */
    String readLine() throws IOException;
}
