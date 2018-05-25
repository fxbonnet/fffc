package com.octo.fffc.formatter;

import com.octo.fffc.exception.InvalidInputException;
import com.octo.fffc.metadata.ColumnDefinition;

import java.util.List;
import java.util.Optional;


/**
 * Provides the contract for converting the incoming {@link String} based on the
 * {@link ColumnDefinition}
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IFormatter {

    /**
     * Formats the given input based on the {@link ColumnDefinition}
     *
     * @param input
     * @param definitions
     * @return
     * @throws InvalidInputException
     */
    Optional<String> format(String input, List<ColumnDefinition> definitions) throws InvalidInputException;
}
