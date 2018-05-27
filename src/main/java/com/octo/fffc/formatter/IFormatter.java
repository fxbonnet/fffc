package com.octo.fffc.formatter;

import com.octo.fffc.metadata.ColumnDefinition;

import java.util.List;


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
     */
    String[] format(String input, List<ColumnDefinition> definitions);
}
