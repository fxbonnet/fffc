package com.octo.fffc.handlers;

import java.io.IOException;

/**
 * Provides a contract for writing a line in CSV format into the file
 * <p>
 * Note that the contract gives us the ability to replace or use a more complex csv writers,
 * from open source or third party libraries (like SuperCsv, Flatpack...etc) for future use cases
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CsvWriter extends AutoCloseable {

    /**
     * @throws IOException
     */
    void flush() throws IOException;

    /**
     * @param input
     * @throws IOException
     */
    void write(String[] input) throws IOException;
}
