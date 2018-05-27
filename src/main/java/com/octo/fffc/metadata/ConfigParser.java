package com.octo.fffc.metadata;

import com.octo.fffc.exception.InvalidInputException;

import java.util.List;

/**
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ConfigParser {

    /**
     * Reads the file to understand the column definition
     *
     * @param filePath
     * @return list of {@link ColumnDefinition}
     * @throws InvalidInputException
     */
    List<ColumnDefinition> parseFile(String filePath);
}
